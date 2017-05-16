package com.guuguo.gank.base

import android.view.View
import butterknife.Unbinder
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.gank.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment : LBaseFragment() {
    private var unbinder: Unbinder? = null

    protected var myApplication = MyApplication.instance
    protected var contentView: View? = null

    abstract fun initPresenter()

    override fun init(view: View) {
        initPresenter()
        super.init(view)
    }
}
