package com.guuguo.gank.app.gank.activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.guuguo.android.lib.extension.*
import com.guuguo.gank.R

import com.guuguo.gank.BuildConfig
import com.guuguo.gank.app.gank.viewmodel.WebViewModel
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.databinding.ActivityWebviewBinding
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.source.GankRepository
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_webview.*
import com.just.agentweb.BaseIndicatorView
import com.just.agentweb.NestedScrollAgentWebView

open class WebViewActivity : BaseActivity() {

    lateinit var binding: ActivityWebviewBinding
    val viewModel by lazy { WebViewModel() }

    open fun getUrl() = mUrl
    open fun getWebContentParentView() = findViewById<ViewGroup>(R.id.content)
    open fun getCustomIndicator(): BaseIndicatorView? = null

    override fun getToolBar(): Toolbar? = binding.bottombar
    override fun getMenuResId() = R.menu.web_menu
    override fun getLayoutResId() = R.layout.activity_webview
    override fun isNavigationBack() = true

    lateinit var mAgentWeb: AgentWeb

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    companion object {
        val ARG_GANK = "ARG_GANK"
        fun intentTo(bean: GankModel, activity: Activity) {
            var intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(ARG_GANK, bean)
            activity.startActivity(intent)
            //设置切换动画，从右边进入，左边退出  n
//            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
        myClipboard.primaryClip = myClip
        showTip("已成功复制")
    }
    override fun initView() {
        super.initView()
        GankRepository.getGankById(gank._id)
                .doOnSuccess {
                    isFavorite = it != null
                    refreshFab()
                 }
                .doOnComplete {
                    refreshFab()
                }.subscribe()
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getWebContentParentView()!!, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                .run {
                    getCustomIndicator()?.run {
                        setCustomIndicator(this)
                    } ?: useDefaultIndicator()
                }
                .setWebView(NestedScrollAgentWebView(activity))
                .setWebViewClient(object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.refresh.isRefreshing = false
                    }
                })
                .createAgentWeb()
                .ready()
                .go(getUrl())
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        binding.refresh.setOnRefreshListener {
            mAgentWeb.urlLoader.reload()
        }
//        binding.bottombar.inflateMenu(getMenuResId())
        binding.fab.setOnClickListener {
            if (isFavorite) {
                GankRepository.deleteGank(gank).doOnComplete {
                    "已成功移除收藏".toast()
                    isFavorite = !isFavorite
                    refreshFab()
                }.subscribe()
            } else {
                GankRepository.insertGank(gank).doOnComplete {
                    "已成功加入收藏".toast()
                    isFavorite = !isFavorite
                    refreshFab()
                }.subscribe()
            }

        }
    }

    private fun refreshFab() {
        if (!isFavorite) {
            binding.fab.setImageResource(R.mipmap.star)
            binding.fab.backgroundTintList = ColorStateList.valueOf(getColorCompat(R.color.colorAccent))
        } else {
            binding.fab.setImageResource(R.drawable.widget_state_success)
            binding.fab.backgroundTintList = ColorStateList.valueOf(getColorCompat(R.color.color_blue_light))
        }
    }

    override fun onBackPressedSupport() {
        val back = mAgentWeb.back()
        if (!back)
            super.onBackPressedSupport()
    }

    var mUrl: String = ""
    var desc: String = ""
    lateinit var gank: GankModel

    var isFavorite = false
    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        gank = intent.getSerializableExtra(ARG_GANK) as GankModel
        mUrl = gank.url
        desc = gank.desc
    }

    override fun getHeaderTitle(): String {
        return desc.safe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_browser -> openInBrowser(mUrl)
            R.id.menu_copy -> copyUrl(mUrl)
            R.id.menu_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, mUrl)
                startActivity(Intent.createChooser(intent, "分享链接到"))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun showTip(msg: String) {
        Snackbar.make(container_view, msg, Snackbar.LENGTH_SHORT)
    }

}
