package com.guuguo.gank.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.dialog.utils.dialogDismiss
import com.guuguo.android.dialog.utils.dialogLoadingShow
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.gank.R
import netError

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class CBaseFragment :LBaseFragment(){
    override fun getLayoutResId()=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
