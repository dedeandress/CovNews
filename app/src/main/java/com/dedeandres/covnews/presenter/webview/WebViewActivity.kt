package com.dedeandres.covnews.presenter.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dedeandres.covnews.R
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(ARG_URL)
        setContentView(R.layout.activity_web_view)

        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_bar.show()
                web_view.hide()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_bar.hide()
                web_view.show()
            }
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url)
                return false // then it is not handled by default action
            }

        }
        web_view.loadUrl(url)
    }

    companion object {
        private const val ARG_URL = "URL"
        fun startFromDashboard(context: Context, url: String) {
            Intent(context, WebViewActivity::class.java).apply {
                putExtra(ARG_URL, url)
                context.startActivity(this)
            }
        }
    }
}
