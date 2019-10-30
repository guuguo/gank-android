package com.guuguo.gank.base

import android.os.Bundle
import android.view.View
import com.guuguo.android.lib.app.LBaseFragment

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class CBaseFragment :LBaseFragment(){
    override fun getLayoutResId()=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
