package com.guuguo.learnsave.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.lib.view.RatioImageView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.R.id.activity
import com.guuguo.learnsave.R.id.toolbar_layout
import com.guuguo.learnsave.ui.adapter.GankAdapter
import com.guuguo.learnsave.ui.base.BaseActivity
import com.guuguo.learnsave.extension.date
import com.guuguo.learnsave.extension.getDateSimply
import com.guuguo.learnsave.extension.safe
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.DateGankPresenter
import com.guuguo.learnsave.app.MEIZI
import com.guuguo.learnsave.app.OmeiziDrawable
import com.guuguo.learnsave.app.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.view.IDateGankView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import kotlinx.android.synthetic.main.toolbar_gank_detail.*
import kotterknife.bindView
import java.util.*

class GankActivity : BaseActivity(), IDateGankView {


    val contentView by bindView<View>(R.id.activity)
    val mIvMeizi by bindView<RatioImageView>(R.id.iv_head)
    val mRecycler by bindView<RecyclerView>(R.id.rv_gank)
    val mProgress by bindView<SmoothProgressBar>(R.id.progressbar)
    val mGankAdapter by lazy {
        GankAdapter()
    }

    override fun getToolBarResId(): Int {
        return R.layout.toolbar_gank_detail
    }


    override fun getHeaderTitle(): String {
        return mGankBean?.desc.safe()
    }

    var mGankBean: GankModel? = null


    val mPresenter by lazy { DateGankPresenter(activity, this) }

    override fun initPresenter() {
        mPresenter.init()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_gank
    }

    override fun initVariable() {
        mGankBean = intent.getSerializableExtra(MEIZI) as GankModel?
        super.initVariable()
    }

    override fun initIView() {
        initIvMeizi()
        mPresenter.fetchDate(mGankBean!!.publishedAt!!)
        initRecycler()
    }

    private fun initRecycler() {
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mRecycler.adapter = mGankAdapter
        mGankAdapter.onItemChildClickListener = object : BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
              val bean=  mGankAdapter.getItem(position)
                if (view!!.id == R.id.tv_content) {
                    WebViewActivity.loadWebViewActivity(bean.url,bean.desc, activity)
                    return true
                }
                return false
            }
        }
    }

    private fun initIvMeizi() {
        mIvMeizi.setImageDrawable(OmeiziDrawable)
        mIvMeizi.setOriginalSize(mGankBean!!.width, mGankBean!!.height)
        ViewCompat.setTransitionName(mIvMeizi, TRANSLATE_GIRL_VIEW)
    }

    override fun showDate(date: ArrayList<GankModel>) {
        mGankAdapter.setNewData(date)
    }

    override fun hideProgress() {
        mProgress.visibility = View.GONE
    }

    override fun showErrorView(e: Throwable) {
        showSnackTip(contentView, e.message.safe())
    }

    override fun showProgress() {
        mProgress.visibility = View.VISIBLE
    }

    override fun showTip(msg: String) {
        showSnackTip(contentView, msg)
    }
}
