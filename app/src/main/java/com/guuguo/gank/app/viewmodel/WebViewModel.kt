package com.guuguo.gank.app.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.net.Uri
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guuguo.gank.app.activity.WebViewActivity


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class WebViewModel(val activity: WebViewActivity) : BaseObservable() {

    fun loadUrl(mWebView: WebView, url: String) {
        activity.binding.progressbar.visibility = View.VISIBLE
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                activity.binding.progressbar.progress = newProgress
                if (newProgress < 60) {
                    activity.binding.progressbar.visibility = View.VISIBLE
                    activity.binding.progressbar.alpha = 1f
                } else if (newProgress == 100)
                    activity.binding.progressbar.visibility = View.GONE
                else if (newProgress >= 60) {
                    activity.binding.progressbar.alpha = (100 - newProgress) * 0.025f
                }
                super.onProgressChanged(view, newProgress)
            }
        })
        val webSettings = mWebView.getSettings()
        //设置WebView属性，能够执行Javascript脚本    
        webSettings.setJavaScriptEnabled(true)
        //设置可以访问文件  
        webSettings.setAllowFileAccess(true)
        //设置支持缩放  
        webSettings.setBuiltInZoomControls(true)
        //加载需要显示的网页    
        //设置Web视图    
        mWebView.loadUrl(url)
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url);
                return true
            }
        })
    }

    fun openInBrowser(url: String) {
        val intent = Intent();
        intent.setAction("android.intent.action.VIEW");
        val content_url = Uri.parse(url);
        intent.setData(content_url);
        activity.startActivity(intent);
    }

    fun copyUrl(url: String) {
        val myClipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData
        val text = url
        myClip = ClipData.newPlainText("text", text)
        myClipboard.setPrimaryClip(myClip)
        activity.showTip("已成功复制")
    }
}

