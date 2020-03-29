package com.dedeandres.covnews.presenter.indonesiadata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.SharedViewModel
import com.dedeandres.covnews.presenter.dashboard.entity.IndonesiaDataResult
import com.dedeandres.covnews.presenter.indonesiadata.adapter.IndonesiaDataAdapter
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.ResourceState
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.activity_indonesia_data.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.koin.android.ext.android.inject

class IndonesiaDataActivity : AppCompatActivity() {

    private val viewModel by inject<SharedViewModel>()

    private val indonesiaDataAdapter by lazy {
        IndonesiaDataAdapter().apply {
            setRecyclerView(rv_indonesia)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indonesia_data)

        rv_indonesia.adapter = indonesiaDataAdapter

        setupToolbar()

        viewModel.indonesiaDataLiveData.observe(this, Observer(::handleFetchIndonesiaData))
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchIndonesiaData()
    }

    private fun handleFetchIndonesiaData(result: Resource<List<IndonesiaDataResult>>) {
        when(result.state) {
            ResourceState.LOADING -> {
                progress_bar?.show()
            }
            ResourceState.SUCCESS -> {
                progress_bar?.hide()
                rv_indonesia?.show()
                result.data?.let {
                    indonesiaDataAdapter.bind(it)
                }
            }
            ResourceState.ERROR -> {

            }
        }

    }

    private fun setupToolbar() {
        iv_info.hide()
        rl_back.show()
        iv_setting.hide()

        rl_back.setOnClickListener{
            finish()
        }
    }

    companion object {
        fun startFromDashboard(context: Context) {
            Intent(context, IndonesiaDataActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }


}
