package com.dedeandres.covnews.presenter.globaldata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.DashboardViewModel
import com.dedeandres.covnews.presenter.dashboard.adapter.DashboardAdapter
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.ResourceState
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.activity_global_data.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class GlobalDataActivity : AppCompatActivity() {

    private val viewModel by inject<DashboardViewModel>()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_data)

        setupToolbar()

        dashboardAdapter = DashboardAdapter()

        dashboardAdapter.setRecyclerView(rv_country_data)
        rv_country_data.adapter = dashboardAdapter

        viewModel.globalDataLiveData.observe(this, Observer(::handleGlobalData))

    }

    private fun handleGlobalData(result: Resource<List<GlobalDataResult>>) {
        when (result.state) {
            ResourceState.LOADING -> {
            }
            ResourceState.SUCCESS -> {
                Timber.d("handleGlobalData: ${result.data}")
                result.data?.let {
                    dashboardAdapter.bind(it)
                }

            }
            ResourceState.ERROR -> {

            }
        }
    }

    private fun setupToolbar() {
        iv_info.hide()
        rl_back.show()

        rl_back.setOnClickListener{
            finish()
        }
    }

    companion object {
        fun startFromDashboard(context: Context) {
            Intent(context, GlobalDataActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
