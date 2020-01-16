package com.guuguo.gank.ui.gank.activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.extension.safe
import com.guuguo.baselib.mvvm.MBaseActivity
import com.guuguo.baselib.widget.WebLoadingIndicator
import com.guuguo.gank.BuildConfig
import com.guuguo.gank.R
import com.guuguo.gank.ui.gank.viewmodel.WebViewModel
import com.guuguo.gank.databinding.ActivityWebviewBinding
import com.guuguo.gank.model.entity.GankModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.BaseIndicatorView
import com.just.agentweb.NestedScrollAgentWebView
import kotlinx.android.synthetic.main.activity_webview.*

open class WebViewActivity : MBaseActivity<ActivityWebviewBinding>() {

    val viewModel by lazy { WebViewModel() }

    open fun getUrl() = mUrl
    open fun getWebContentParentView() = findViewById<ViewGroup>(R.id.content)
    open fun getCustomIndicator(): BaseIndicatorView? = WebLoadingIndicator(activity)

    override fun getToolBar(): Toolbar? = binding.bottombar
    override fun getMenuResId() = R.menu.web_menu
    override fun getLayoutResId() = R.layout.activity_webview
    override fun isNavigationBack() = true

    lateinit var mAgentWeb: AgentWeb

    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        setupBaseViewModel(viewModel)
        viewModel.isFavorite.observe(this, Observer {
            if (it != true) {
                binding.fab.setImageResource(R.mipmap.star)
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColorCompat(R.color.colorAccent))
            } else {
                binding.fab.setImageResource(R.drawable.widget_state_success)
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColorCompat(R.color.color_blue_light))
            }
        })
    }

    companion object {
        val ARG_GANK = "ARG_GANK"
        val ACTIVITY_WEBVIEW = 0x102
        fun intentTo(bean: GankModel, activity: Activity) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(ARG_GANK, bean as Parcelable)
            activity.startActivityForResult(intent, ACTIVITY_WEBVIEW)
        }

        fun intentTo(bean: GankModel, fragment: LBaseFragment) {
            val intent = Intent(fragment.activity, WebViewActivity::class.java)
            intent.putExtra(ARG_GANK, bean as Parcelable)
            fragment.startActivityForResult(intent, ACTIVITY_WEBVIEW)
        }
    }

    override fun initView() {
        super.initView()
        viewModel.checkFavorite()
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getWebContentParentView()!!, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                .run {
                    getCustomIndicator()?.run {
                        setCustomIndicator(this)
                    } ?: useDefaultIndicator()
                }
                .setWebView(NestedScrollAgentWebView(activity))
//                .setWebViewClient(object : WebViewClient() {
//                    override fun onPageFinished(view: WebView?, url: String?) {
//                        super.onPageFinished(view, url)
//                        binding.refresh.isRefreshing = false
//                    }
//                })
                .createAgentWeb()
                .ready()
                .go(getUrl())
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
//        binding.refresh.setOnRefreshListener {
//            mAgentWeb.urlLoader.reload()
//        }
        binding.fab.setOnClickListener {
            viewModel.likeChanged()
        }
    }

    override fun onBackPressed() {
        val back = mAgentWeb.back()
        if (!back) super.onBackPressed()
    }

    var mUrl: String = ""
    var desc: String = ""

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        viewModel.gank = intent.getParcelableExtra(ARG_GANK) as GankModel
        mUrl = viewModel.gank.url.safe()
        desc = viewModel.gank.desc.safe()
    }

    override fun getHeaderTitle(): String {
        return desc.safe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_browser -> openInBrowser(mUrl)
            R.id.menu_copy -> copyUrl(mUrl)
            R.id.menu_share -> {
                shareUrl()
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
        showTip("已成功复制")
    }

    private fun shareUrl() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, mUrl)
        startActivity(Intent.createChooser(intent, "分享链接到"))
    }

    fun showTip(msg: String) {
        Snackbar.make(container_view, msg, Snackbar.LENGTH_SHORT).show()
    }

}
