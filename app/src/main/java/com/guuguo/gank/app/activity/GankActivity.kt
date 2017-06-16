package com.guuguo.gank.app.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.showSnackTip
import com.guuguo.gank.R
import com.guuguo.gank.app.adapter.CategoryGankAdapter
import com.guuguo.gank.base.BaseActivity
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.presenter.DateGankPresenter
import com.guuguo.gank.constant.MEIZI
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.view.IDateGankView
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.android.synthetic.main.toolbar_gank_detail.*
import java.util.*

class GankActivity : BaseActivity(), IDateGankView {
    companion object {
        val TRANSLATE_GIRL_VIEW = "share_girl_image_view"

        fun intentTo(activity: Activity, image: View, meizi: GankModel) {
            val intent = Intent(activity, GankActivity::class.java)
            intent.putExtra(MEIZI, meizi)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, TRANSLATE_GIRL_VIEW)
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
            
        }
    }

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

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setHeightAndPadding(activity, getToolBar())
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
                    WebViewActivity.intentTo(bean.url, bean.desc, activity)
                    return true
                }
                return false
            }
        }
    }

    override fun onBackPressedSupport() {
        supportFinishAfterTransition()
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
