package com.dedeandres.covnews.presenter.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.adapter.DashboardAdapter
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.ResourceState
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_dashboard_data.*
import kotlinx.android.synthetic.main.layout_shimmer_data.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class DashboardActivity : AppCompatActivity() {

    private val viewModel by inject<DashboardViewModel>()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.globalDataLiveData.observe(this, Observer(::handleGlobalData))
        dashboardAdapter = DashboardAdapter()

        dashboardAdapter.setRecyclerView(rv_country_data)
        rv_country_data.adapter = dashboardAdapter

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

    override fun onStart() {
        super.onStart()

        viewModel.fetchGlobalData()
        setupSwipeRefreshLayout()
    }

    private fun handleGlobalData(result: Resource<List<GlobalDataResult>>) {
        when (result.state) {
            ResourceState.LOADING -> {
                swipe_refresh_layout.isRefreshing = true
                include_dashboard.hide()
                include_shimmer.show()
                sl_dashboard.startShimmerAnimation()
            }
            ResourceState.SUCCESS -> {
                swipe_refresh_layout.isRefreshing = false
                include_dashboard.show()
                include_shimmer.hide()
                sl_dashboard.stopShimmerAnimation()
                Timber.d("handleGlobalData: ${result.data}")
                result.data?.let {
                    dashboardAdapter.bind(it)
                }
                val indonesia = result.data?.find {
                    it.id == (INDONESIA_ID)
                }

                tv_total_death.text = indonesia?.deaths
                tv_total_positive.text = indonesia?.confirmed
                tv_total_recovered.text = indonesia?.recovered
            }
            ResourceState.ERROR -> {

            }
        }
    }

    companion object {
        const val INDONESIA_ID = "65"
    }

}
