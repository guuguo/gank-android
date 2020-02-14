package com.guuguo.baselib.mvvm

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.lib.ktx.snackShow
import com.guuguo.baselib.R
import com.guuguo.baselib.databinding.BaseActivityWebviewBinding
import com.guuguo.baselib.widget.WebLoadingIndicator
import com.just.agentweb.AgentWeb
import com.just.agentweb.BaseIndicatorView
import com.just.agentweb.BuildConfig
import com.just.agentweb.NestedScrollAgentWebView

open class FullscreenWebViewActivity : MBaseActivity<BaseActivityWebviewBinding>() {


    open fun getUrl() = mUrl
    open fun getWebContentParentView() = findViewById<ViewGroup>(R.id.content)
    open fun getCustomIndicator(): BaseIndicatorView? = WebLoadingIndicator(activity)

    override fun getLayoutResId() = R.layout.base_activity_webview
    override fun isNavigationBack() = true
    override fun getToolBar(): Toolbar? = binding.bottombar
    override fun getHeaderTitle(): String = title.safe()
    override fun getMenuResId() = R.menu.web_menu

    lateinit var mAgentWeb: AgentWeb

    companion object {
        val ARG_URL = "url"
        val ACTIVITY_WEBVIEW = 0x102
        fun intentTo(activity: Activity, url: String) {
            val intent = Intent(activity, FullscreenWebViewActivity::class.java)
            intent.putExtra(ARG_URL, url)
            activity.startActivityForResult(intent, ACTIVITY_WEBVIEW)
        }
    }

    override fun initView() {
        super.initView()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                getWebContentParentView()!!,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            .run {
                getCustomIndicator()?.run {
                    setCustomIndicator(this)
                } ?: useDefaultIndicator()
            }
            .setWebView(NestedScrollAgentWebView(activity))
            .createAgentWeb()
            .ready()
            .go(getUrl())
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }
    }

    override fun onBackPressed() {
        val back = mAgentWeb.back()
        if (!back) super.onBackPressed()
    }

    var mUrl: String = ""
    var title: String = ""

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mUrl = intent.getStringExtra(ARG_URL)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val url = mAgentWeb.webCreator.webView.url
        when (item.itemId) {
            R.id.menu_browser -> openInBrowser(url)
            R.id.menu_copy -> copyUrl(url)
            R.id.menu_share -> {
                shareUrl(url)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

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
        myClipboard.setPrimaryClip(myClip)
        snackShow("已成功复制")
    }

    private fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(intent, "分享链接到"))
    }

}
