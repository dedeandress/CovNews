package com.dedeandres.covnews.presenter.dashboard.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.akexorcist.localizationactivity.core.LanguageSetting
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.DashboardActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_setting_bottomsheet.*
import timber.log.Timber
import java.util.*


class SettingBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CommonBottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet =
                dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

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
        initRadioButtonLanguage()
        rg_lang.setOnCheckedChangeListener(radioButtonListener)
    }

    private val radioButtonListener = object : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            when(checkedId) {
                R.id.rb_indonesia -> {
                    dismiss()
                    setNewLocale(Locale("in"))
                    Timber.d("Language ID: ${LanguageSetting.getLanguage(requireContext())}")
                }

                R.id.rb_english -> {
                    dismiss()
                    setNewLocale(Locale.ENGLISH)
                    Timber.d("Language EN: ${LanguageSetting.getLanguage(requireContext())}")
                }

            }
        }
    }

    private fun setNewLocale(
        locale: Locale
    ) {
        LanguageSetting.setLanguage(requireContext(), locale)
        DashboardActivity.startFromDashboard(requireContext())
    }

    private fun initRadioButtonLanguage() {
        if (LanguageSetting.getLanguage(requireContext()) == Locale.ENGLISH) {
            rb_english.isChecked = true
            rb_indonesia.isChecked = false
        } else {
            rb_english.isChecked = false
            rb_indonesia.isChecked = true
        }
    }

    companion object {
    }

}