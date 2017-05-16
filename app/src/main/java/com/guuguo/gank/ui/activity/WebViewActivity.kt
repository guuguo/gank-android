package com.guuguo.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.guuguo.gank.R

import com.guuguo.gank.presenter.WebViewPresenter
import com.guuguo.gank.view.IBaseView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import android.webkit.WebView
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.base.BaseSwipeBackActivity
import kotterknife.bindView


class WebViewActivity : BaseSwipeBackActivity(), IBaseView {

    val contentView by bindView<View>(R.id.activity)
    val mProgress by bindView<SmoothProgressBar>(R.id.progressbar)
    val mWebView by bindView<WebView>(R.id.wv_web)

    var url: String = ""
    var desc: String = ""
    val mPresenter by lazy { WebViewPresenter(activity, this) }

    companion object {
        val ARG_URL = "ARG_URL"
        val ARG_DESC = "ARG_DESC"
        fun loadWebViewActivity(url: String, desc: String, context: Context) {
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(ARG_URL, url)
            intent.putExtra(ARG_DESC, desc)
            context.startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack())
            mWebView.goBack()
        else
            super.onBackPressed()
    }

    override fun initVariable() {
        url = intent.getStringExtra(ARG_URL)
        desc = intent.getStringExtra(ARG_DESC)
    }

    override fun getHeaderTitle(): String {
        return desc.safe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_browser -> mPresenter.openInBrowser(url)
            R.id.menu_copy -> mPresenter.copyUrl(url)
            R.id.menu_share -> {
                val intent = Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(intent, "分享链接到"));
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_webview
    }


    override fun initPresenter() {
        mPresenter.init()
    }

    override fun initIView() {
        mPresenter.loadUrl(mWebView, url)
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
