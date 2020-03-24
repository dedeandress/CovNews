package com.dedeandres.covnews.presenter.dashboard.bottomsheet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.dedeandres.covnews.R
import com.dedeandres.covnews.data.dashboard.entity.Hotline
import com.dedeandres.covnews.presenter.dashboard.DashboardViewModel
import com.dedeandres.covnews.presenter.dashboard.adapter.HotlineAdapter
import com.dedeandres.covnews.util.Resource
import com.dedeandres.covnews.util.ResourceState
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_info_bottomsheet.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class InfoBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel by inject<DashboardViewModel>()
    private val hotlineAdapter by lazy {
        HotlineAdapter()
    }

    private val downArrowDrawable by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_down_arrow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CommonBottomSheetDialog)

        viewModel.hotlineLiveData.observe(this, Observer(::handleHotline))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_detail_hotline.adapter = hotlineAdapter
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dismiss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    }

                })
            }


        }

        initView()
    }

    private fun initView() {
        viewModel.fetchHotline()

        cv_hotline.setOnClickListener(this)
        cv_wash_hand.setOnClickListener(this)
        cv_wash_time.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.avoidDoubleClicks()
        when(v?.id) {
            R.id.cv_hotline -> {
                Timber.d("hotline click")
                handleShowAndHide(iv_expand_hotline, rv_detail_hotline, cv_hotline)
            }
            R.id.cv_wash_hand -> {
                Timber.d("wash hand click")
                handleShowAndHide(iv_expand_wash_hand, ll_detail_wash_hand, cv_wash_hand)
            }
            R.id.cv_wash_time -> {
                Timber.d("wash time click")
                handleShowAndHide(iv_expand_wash_time, ll_detail_wash_time, cv_wash_time)
            }
        }
    }

    private fun handleShowAndHide(imageView: ImageView, view: View, cardView: CardView) {
        if (imageView.drawable.constantState == downArrowDrawable?.constantState) {
            imageView.setImageResource(R.drawable.ic_up_arrow)
            view.show()
        }else {
            imageView.setImageResource(R.drawable.ic_down_arrow)
            view.hide()
        }
    }

    private fun handleHotline(resource: Resource<List<Hotline>>) {
        when(resource.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                Timber.d("data: ${resource.data}")
                resource.data?.let {
                    hotlineAdapter.bind(resource.data)
                }
            }

            ResourceState.ERROR -> {

            }
        }
    }

    companion object {
    }

}