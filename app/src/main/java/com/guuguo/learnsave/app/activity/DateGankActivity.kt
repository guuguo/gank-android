package com.guuguo.learnsave.app.activity

import android.view.View
import butterknife.bindView
import com.guuguo.learnsave.R
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.model.GankDays
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.DateGankPresenter
import com.guuguo.learnsave.view.IDateGankView

class DateGankActivity : ToolBarActivity(), IDateGankView {

    val contentView by bindView<View>(R.id.activity)

    companion object {
        val FIELD_DATE = "date"
    }

    val dateGankPresenter by lazy { DateGankPresenter(activity, this) }

    override fun initPresenter() {
        dateGankPresenter.init()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_date_gank
    }

    override fun initIView() {

    }

    override fun showDate(date: GankDays) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorView(e: Throwable) {
        showSnackTip(contentView, e.message!!)
    }

    override fun showProgress() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
