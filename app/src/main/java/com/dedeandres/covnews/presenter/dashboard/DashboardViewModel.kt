package com.dedeandres.covnews.presenter.dashboard

import androidx.lifecycle.MutableLiveData
import com.dedeandres.covnews.domain.dashboard.usecase.DashboardUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.NewsUseCase
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.base.BaseViewModel
import com.dedeandres.covnews.util.ext.with
import com.dedeandres.covnews.util.rx.SchedulerProvider
import com.dedeandres.covnews.util.setErrorEvent
import com.dedeandres.covnews.util.setLoadingEvent
import com.dedeandres.covnews.util.setSuccessEvent
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class DashboardViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val dashboardUseCase: DashboardUseCase,
    private val newsUseCase: NewsUseCase
) : BaseViewModel() {

    val globalDataLiveData = MutableLiveData<Resource<List<GlobalDataResult>>>()
    val newsLiveData = MutableLiveData<Resource<List<NewsResult>>>()

    fun fetchGlobalData() {
        globalDataLiveData.setLoadingEvent()
        dashboardUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(
                onSuccess = {
                    Timber.d("fetchGlobalData: $it ")
                    globalDataLiveData.setSuccessEvent(it)
                },
                onError = {
                    Timber.d("Global Data $it")
                    globalDataLiveData.setErrorEvent(it)
                }
            ).collect()

    }

    fun fetchNews() {
        newsLiveData.setLoadingEvent()
        newsUseCase.execute()
            .with(schedulerProvider)
            .subscribeBy(onSuccess = {
                Timber.d("News: $it size: ${it.size}")
                newsLiveData.setSuccessEvent(it)
            },
            onError = {
                Timber.e("News: $it")
                newsLiveData.setErrorEvent(it)
            })
    }

}