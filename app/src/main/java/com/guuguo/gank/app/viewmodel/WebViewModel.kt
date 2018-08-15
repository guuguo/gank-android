package com.guuguo.gank.app.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.net.Uri
import com.guuguo.gank.app.activity.WebViewActivity


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class WebViewModel(val activity: WebViewActivity) : BaseObservable() {

    fun openInBrowser(url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val contentUrl = Uri.parse(url)
        intent.data = contentUrl
        activity.startActivity(intent)
    }

    fun copyUrl(url: String) {
        val myClipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("text", url)
        myClipboard.primaryClip = myClip
        activity.showTip("已成功复制")
    }
}

