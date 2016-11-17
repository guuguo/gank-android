package com.guuguo.learnsave.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.model.retrofit.ApiServer
import com.guuguo.learnsave.view.IBaseView
import com.guuguo.learnsave.view.IDateGankView
import com.guuguo.learnsave.view.IMainView
import rx.functions.Action1
import java.util.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE





/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
class WebViewPresenter(context: Context, iView: IBaseView) : BasePresenter<IBaseView>(context, iView) {
    fun loadUrl(mWebView: WebView, url: String) {
        iView.showProgress()
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.d("guuguo", newProgress.toString())
                if (newProgress == 100)
                    iView.hideProgress()
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
        context.startActivity(intent);
    }

    fun  copyUrl(url: String) {
        val myClipboard: ClipboardManager
        myClipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager 
        val myClip: ClipData
        val text = url
        myClip = ClipData.newPlainText("text", text)
        myClipboard.setPrimaryClip(myClip)
        iView.showTip("已成功复制")
    }

}
