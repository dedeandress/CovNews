package com.dedeandres.covnews.presenter.dashboard

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.adapter.DashboardAdapter
import com.dedeandres.covnews.presenter.dashboard.adapter.NewsAdapter
import com.dedeandres.covnews.presenter.dashboard.bottomsheet.InfoBottomSheetFragment
import com.dedeandres.covnews.presenter.dashboard.bottomsheet.SettingBottomSheetFragment
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.presenter.globaldata.GlobalDataActivity
import com.dedeandres.covnews.presenter.indonesiadata.IndonesiaDataActivity
import com.dedeandres.covnews.presenter.webview.WebViewActivity
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.ResourceState
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_dashboard_data.*
import kotlinx.android.synthetic.main.layout_shimmer_data.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*

class DashboardActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener, View.OnClickListener, OnLocaleChangedListener {

    private val localizationDelegate = LocalizationActivityDelegate(this)

    private val viewModel by inject<SharedViewModel>()
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var newsAdapter: NewsAdapter

    private val infoBottomSheet by lazy {
        InfoBottomSheetFragment()
    }
    private val settingBottomSheet by lazy {
        SettingBottomSheetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.globalDataLiveData.observe(this, Observer(::handleGlobalData))
        viewModel.newsLiveData.observe(this, Observer(::handleNews))
        dashboardAdapter = DashboardAdapter()
        newsAdapter = NewsAdapter()

        dashboardAdapter.setRecyclerView(rv_country_data)
        rv_country_data.adapter = dashboardAdapter
        rv_news.adapter = newsAdapter
        newsAdapter.setItemClickListener(this)
        iv_info.setOnClickListener(this)
        iv_setting.setOnClickListener(this)
        tv_see_all_global.setOnClickListener(this)
        cv_indonesia.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        v?.avoidDoubleClicks()
        when(v?.id) {
            R.id.iv_info -> {
                infoBottomSheet.show(supportFragmentManager, infoBottomSheet.tag)
            }
            R.id.tv_see_all_global -> {
                GlobalDataActivity.startFromDashboard(this)
            }
            R.id.cv_indonesia -> {
                IndonesiaDataActivity.startFromDashboard(this)
            }
            R.id.iv_setting -> {
                settingBottomSheet.show(supportFragmentManager, settingBottomSheet.tag)
            }
        }
    }

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout?.apply {
            isEnabled = true

            setProgressViewOffset(
                false,
                resources.getDimensionPixelSize(R.dimen.dp_0),
                resources.getDimensionPixelSize(R.dimen.dp_32)
            )

            setOnRefreshListener {
                viewModel.fetchGlobalData()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(newBase))
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchGlobalData()
        setupSwipeRefreshLayout()

        viewModel.fetchNews()
    }

    private fun handleGlobalData(result: Resource<List<GlobalDataResult>>) {
        when (result.state) {
            ResourceState.LOADING -> {
                showShimmer()
            }
            ResourceState.SUCCESS -> {
                hideShimmer()
                Timber.d("handleGlobalData: ${result.data}")
                result.data?.let {
                    dashboardAdapter.bind(it.subList(0, 10))
                }
                val indonesia = result.data?.find {
                    it.country == (INDONESIA_ID)
                }

                tv_total_death.text = indonesia?.deaths
                tv_total_positive.text = indonesia?.confirmed
                tv_total_recovered.text = indonesia?.recovered
                tv_update_date.text = indonesia?.lastUpdate
            }
            ResourceState.ERROR -> {

            }
        }
    }

    private fun handleNews(result: Resource<List<NewsResult>>) {
        when(result.state) {

            ResourceState.LOADING -> {
                showShimmer()
            }

            ResourceState.SUCCESS -> {
                hideShimmer()
                result.data?.let {
                    newsAdapter.bind(it)
                }
            }

            ResourceState.ERROR -> {

            }
         }
    }

    override fun onItemClick(newsResult: NewsResult) {
        Timber.d("onItemClick: $newsResult")
        WebViewActivity.startFromDashboard(this, newsResult.url)
    }

    private fun showShimmer() {
        swipe_refresh_layout.isRefreshing = true
        include_dashboard.hide()
        include_shimmer.show()
        sl_dashboard.startShimmerAnimation()
    }

    private fun hideShimmer() {
        swipe_refresh_layout.isRefreshing = false
        include_dashboard.show()
        include_shimmer.hide()
        sl_dashboard.stopShimmerAnimation()
    }

    companion object {
        const val INDONESIA_ID = "Indonesia"

        fun startFromDashboard(context: Context) {
            Intent(context, DashboardActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

    override fun onAfterLocaleChanged() {}

    override fun onBeforeLocaleChanged() {}

}
