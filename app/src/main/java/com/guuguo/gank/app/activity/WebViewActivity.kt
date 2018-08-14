package com.guuguo.gank.app.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.guuguo.android.lib.extension.inflater
import com.guuguo.android.lib.extension.initNav
import com.guuguo.gank.R

import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.BuildConfig
import com.guuguo.gank.app.activity.WebViewActivity.Companion.ARG_GANK
import com.guuguo.gank.app.viewmodel.WebViewModel
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.databinding.ActivityWebviewBinding
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.source.GankRepository
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.base_toolbar_common.*
import com.just.agentweb.BaseIndicatorView
import com.just.agentweb.NestedScrollAgentWebView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

open class WebViewActivity : BaseActivity() {

    lateinit var binding: ActivityWebviewBinding
    val viewModel by lazy { WebViewModel(this) }

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

    override fun initView() {
        super.initView()
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getWebContentParentView()!!, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
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
            WebView.setWebContentsDebuggingEnabled(true)
        }
//        binding.bottombar.inflateMenu(getMenuResId())
        binding.fab.setOnClickListener {
            "收藏".toast()
            GankRepository.insertGank(gank)
                    .subscribe()
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
            R.id.menu_browser -> viewModel.openInBrowser(mUrl)
            R.id.menu_copy -> viewModel.copyUrl(mUrl)
            R.id.menu_share -> {
                val intent = Intent(Intent.ACTION_SEND);
                intent.type = "text/plain";
                intent.putExtra(Intent.EXTRA_TEXT, mUrl);
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
