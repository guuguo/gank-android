package com.guuguo.gank.app.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guuguo.gank.app.activity.WebViewActivity
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.http.BaseCallback
import com.guuguo.gank.app.fragment.GankDailyFragment
import com.guuguo.gank.constant.Option.context
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class WebViewModel(val activity: WebViewActivity) : BaseObservable() {

    fun loadUrl(mWebView: WebView, url: String) {
        activity.binding.progressbar.visibility = View.VISIBLE
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                Log.d("guuguo", newProgress.toString())
                activity.binding.progressbar.progress = newProgress
                if (newProgress == 100)
                    activity.binding.progressbar.visibility = View.GONE
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
        val myClipboard: ClipboardManager
        myClipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData
        val text = url
        myClip = ClipData.newPlainText("text", text)
        myClipboard.setPrimaryClip(myClip)
        activity.showTip("已成功复制")
    }
}

