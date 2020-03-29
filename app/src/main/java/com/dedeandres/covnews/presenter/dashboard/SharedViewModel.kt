package com.dedeandres.covnews.presenter.dashboard

import androidx.lifecycle.MutableLiveData
import com.dedeandres.covnews.data.dashboard.entity.Hotline
import com.dedeandres.covnews.domain.dashboard.usecase.FetchDashboardUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchIndonesiaDataUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchHotlineUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchNewsUseCase
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.presenter.dashboard.entity.IndonesiaDataResult
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.base.BaseViewModel
import com.dedeandres.covnews.util.ext.with
import com.dedeandres.covnews.util.rx.SchedulerProvider
import com.dedeandres.covnews.util.setError
import com.dedeandres.covnews.util.setLoading
import com.dedeandres.covnews.util.setSuccess
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SharedViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val fetchDashboardUseCase: FetchDashboardUseCase,
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val fetchHotlineUseCase: FetchHotlineUseCase,
    private val fetchIndonesiaDataUseCase: FetchIndonesiaDataUseCase
) : BaseViewModel() {

    val globalDataLiveData = MutableLiveData<Resource<List<GlobalDataResult>>>()
    val newsLiveData = MutableLiveData<Resource<List<NewsResult>>>()
    val hotlineLiveData = MutableLiveData<Resource<List<Hotline>>>()
    val indonesiaDataLiveData = MutableLiveData<Resource<List<IndonesiaDataResult>>>()

    fun fetchGlobalData() {
        globalDataLiveData.setLoading()
        fetchDashboardUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(
                onSuccess = {
                    Timber.d("fetchGlobalData: $it ")
                    globalDataLiveData.setSuccess(it)
                },
                onError = {
                    Timber.d("Global Data $it")
                    globalDataLiveData.setError(it)
                }
            ).collect()

    }

    fun fetchNews() {
        newsLiveData.setLoading()
        fetchNewsUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(onSuccess = {
                Timber.d("News: $it size: ${it.size}")
                newsLiveData.setSuccess(it)
            },
            onError = {
                Timber.e("News: $it")
                newsLiveData.setError(it)
            })
    }

    fun fetchHotline() {
        hotlineLiveData.setLoading()
        fetchHotlineUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(
                onSuccess = {
                    hotlineLiveData.setSuccess(it)
                },
                onError = {
                    hotlineLiveData.setError(it)
                }
            )
    }

    fun fetchIndonesiaData() {
        indonesiaDataLiveData.setLoading()
        fetchIndonesiaDataUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(
                onSuccess = {
                    indonesiaDataLiveData.setSuccess(it)
                },
                onError = {
                    indonesiaDataLiveData.setError(it)
                }
            )
    }

}