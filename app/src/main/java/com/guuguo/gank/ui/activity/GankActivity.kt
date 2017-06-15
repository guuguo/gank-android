package com.guuguo.gank.ui.activity

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.R
import com.guuguo.gank.R.id.*
import com.guuguo.gank.ui.adapter.CategoryGankAdapter
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.presenter.DateGankPresenter
import com.guuguo.gank.constant.MEIZI
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.constant.TRANSLATE_GIRL_VIEW
import com.guuguo.gank.view.IDateGankView
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.android.synthetic.main.toolbar_gank_detail.*
import java.util.*

class GankActivity : BaseActivity(), IDateGankView {

    val mGankAdapter by lazy {
        CategoryGankAdapter()
    }

    override fun getHeaderTitle() = mGankBean?.desc.safe()
    override fun getToolBar() = id_tool_bar
    override fun getLayoutResId() = R.layout.activity_gank

    var mGankBean: GankModel? = null


    val mPresenter by lazy { DateGankPresenter(activity, this) }

    override fun initPresenter() {
        mPresenter.init()
    }


    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mGankBean = intent.getSerializableExtra(MEIZI) as GankModel?
    }

    override fun initIView() {
        initIvMeizi()
        mPresenter.fetchDate(mGankBean!!.publishedAt!!)
        initRecycler()
    }

    private fun initRecycler() {
        rv_gank.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        rv_gank.adapter = mGankAdapter
        mGankAdapter.onItemChildClickListener = object : BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
                val bean = mGankAdapter.getItem(position)
                if (view!!.id == R.id.tv_content) {
                    WebViewActivity.loadWebViewActivity(bean.url, bean.desc, activity)
                    return true
                }
                return false
            }
        }
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        ActivityCompat.finishAfterTransition(activity)
    }
    private fun initIvMeizi() {
        iv_head.setImageDrawable(OmeiziDrawable)
        iv_head.setOriginalSize(mGankBean!!.width, mGankBean!!.height)
        ViewCompat.setTransitionName(iv_head, TRANSLATE_GIRL_VIEW)
    }

    override fun showDate(date: ArrayList<GankModel>) {
        mGankAdapter.setNewData(date)
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun showErrorView(e: Throwable) {
        showSnackTip(container_view, e.message.safe())
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun showTip(msg: String) {
        showSnackTip(container_view, msg)
    }
}
