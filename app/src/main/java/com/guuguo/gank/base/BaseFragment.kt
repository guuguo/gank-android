package com.guuguo.gank.base

import com.guuguo.android.lib.app.LBaseFragmentSupport
import com.guuguo.gank.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment : LBaseFragmentSupport() {
    protected var myApplication = MyApplication.instance

}
