package com.guuguo.gank.app.activity

import android.app.Activity
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.guuguo.android.lib.extension.initNav
import com.guuguo.gank.R

import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.app.viewmodel.WebViewModel
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.databinding.ActivityWebviewBinding
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.base_toolbar_common.*


class WebViewActivity : BaseActivity() {

    lateinit var binding: ActivityWebviewBinding
    val viewModel by lazy { WebViewModel(this) }

    override fun getMenuResId() = R.menu.web_menu
    override fun getLayoutResId() = R.layout.activity_webview
    override fun isNavigationBack() = true
    override fun getToolBar(): Toolbar? {
        return id_tool_bar
    }

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    companion object {
        val ARG_URL = "ARG_URL"
        val ARG_DESC = "ARG_DESC"
        fun intentTo(url: String, desc: String, activity: Activity) {
            var intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(ARG_URL, url)
            intent.putExtra(ARG_DESC, desc)
            activity.startActivity(intent)
            //设置切换动画，从右边进入，左边退出  n
//            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    override fun initToolBar() {
        super.initToolBar()
        getToolBar()?.setNavigationOnClickListener {
            activity.finish()
        }
    }

    override fun onBackPressedSupport() {
        if (wv_web.canGoBack())
            wv_web.goBack()
        else {
            supportFinishAfterTransition()
        }
    }

    var url: String = ""
    var desc: String = ""

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        url = intent.getStringExtra(ARG_URL)
        desc = intent.getStringExtra(ARG_DESC)
    }

    override fun getHeaderTitle(): String {
        return desc.safe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_browser -> viewModel.openInBrowser(url)
            R.id.menu_copy -> viewModel.copyUrl(url)
            R.id.menu_share -> {
                val intent = Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(intent, "分享链接到"));
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun loadData() {
        super.loadData()
        viewModel.loadUrl(wv_web, url)
    }

    fun showTip(msg: String) {
        showSnackTip(container_view, msg)
    }

}
