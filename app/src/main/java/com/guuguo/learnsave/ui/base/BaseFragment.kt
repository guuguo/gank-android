package com.guuguo.learnsave.ui.base

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.learnsave.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment : LBaseFragment() {
    private var unbinder: Unbinder? = null

    protected var myApplication = MyApplication.instance
    protected var contentView: View? = null
    protected var activity: BaseActivity? = null

    abstract fun initPresenter()

    override fun init(view: View) {
        initPresenter()
        super.init(view)
    }
}
