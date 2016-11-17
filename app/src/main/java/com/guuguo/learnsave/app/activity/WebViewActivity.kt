package com.guuguo.learnsave.app.activity

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.GankAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.app.widget.RatioImageView
import com.guuguo.learnsave.extension.date
import com.guuguo.learnsave.extension.getDateSimply
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.BasePresenter
import com.guuguo.learnsave.presenter.DateGankPresenter
import com.guuguo.learnsave.presenter.WebViewPresenter
import com.guuguo.learnsave.util.GANK
import com.guuguo.learnsave.util.MEIZI
import com.guuguo.learnsave.util.OmeiziDrawable
import com.guuguo.learnsave.util.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.view.IBaseView
import com.guuguo.learnsave.view.IDateGankView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import java.util.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import kotterknife.bindView


class WebViewActivity : ToolBarActivity(), IBaseView {

    val contentView by bindView<View>(R.id.activity)
    val mProgress by bindView<SmoothProgressBar>(R.id.progressbar)
    val mWebView by bindView<WebView>(R.id.wv_web)

    var mGankBean: GankModel? = null
    val mPresenter by lazy { WebViewPresenter(activity, this) }

    companion object {
        fun loadWebViewActivity(gank: GankModel, context: Context) {
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(GANK, gank)
            context.startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_browser -> mPresenter.openInBrowser(mGankBean!!.url)
            R.id.menu_copy -> mPresenter.copyUrl(mGankBean!!.url)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_webview
    }

    override fun isBackVisible(): Boolean {
        return true
    }

    override fun initToolBar() {
        super.initToolBar()
        actionBar?.setTitle(mGankBean?.desc)
    }

    override fun initPresenter() {
        mPresenter.init()
    }

    override fun initIView() {
        mGankBean = intent.getSerializableExtra(GANK) as GankModel?
        mPresenter.loadUrl(mWebView, mGankBean!!.url)
    }

    override fun hideProgress() {
        mProgress.visibility = View.GONE
    }

    override fun showErrorView(e: Throwable) {
    }

    override fun showProgress() {
        mProgress.visibility = View.VISIBLE
    }

    override fun showTip(msg: String) {
        showSnackTip(contentView, msg)
    }

}
