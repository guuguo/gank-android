package com.guuguo.learnsave.app.activity

import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.GankAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.app.widget.RatioImageView
import com.guuguo.learnsave.extension.date
import com.guuguo.learnsave.extension.getDateSimply
import com.guuguo.learnsave.extension.safe
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.DateGankPresenter
import com.guuguo.learnsave.util.MEIZI
import com.guuguo.learnsave.util.OmeiziDrawable
import com.guuguo.learnsave.util.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.view.IDateGankView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import kotterknife.bindView
import java.util.*

class GankActivity : ToolBarActivity(), IDateGankView {


    val contentView by bindView<View>(R.id.activity)
    val mIvMeizi by bindView<RatioImageView>(R.id.iv_head)
    val mRecycler by bindView<RecyclerView>(R.id.rv_gank)
    val mProgress by bindView<SmoothProgressBar>(R.id.progressbar)
    val mGankAdapter by lazy {
        GankAdapter()
    }

    var mGankBean: GankModel? = null


    val mPresenter by lazy { DateGankPresenter(activity, this) }

    override fun initPresenter() {
        mPresenter.init()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_gank
    }

    override fun isBackVisible(): Boolean {
        return true
    }

    override fun initToolBar() {
        super.initToolBar()
        actionBar?.setTitle(mGankBean?.desc)
    }

    override fun initIView() {
        mGankBean = intent.getSerializableExtra(MEIZI) as GankModel?
        initIvMeizi()
        mPresenter.fetchDate(mGankBean!!.publishedAt!!)
        initRecycler()
    }

    private fun initRecycler() {
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mRecycler.adapter = mGankAdapter
        mRecycler.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun SimpleOnItemChildClick(adapter: BaseQuickAdapter<*>?, view: View?, i: Int) {
                if (view!!.id == R.id.tv_content)
                    WebViewActivity.loadWebViewActivity(mGankAdapter.getItem(i), activity)
            }
        })
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
