package com.guuguo.gank.app.fuliba.main

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.guuguo.android.dialog.dialog.NormalListDialog
import com.guuguo.android.dialog.utils.OnOperItemClickL
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.utils.DisplayUtil
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.AboutActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.base.CBaseFragment
import com.guuguo.gank.databinding.FragmentHomeBinding
import com.guuguo.gank.util.ThemeUtils
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.proguard.t
import com.trello.rxlifecycle2.components.RxFragment
import kotlinx.android.synthetic.main.base_toolbar_common.*
import kotlinx.android.synthetic.main.base_toolbar_common.view.*

class FulibaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
//    @Composable
//    fun NewsStory() {
//        Column(
//            crossAxisSize = LayoutSize.Expand,
//            modifier=Spacing(16.dp)
//        ) {
//            Text("A day in Shark Fin Cove")
//            Text("Davenport, California")
//            Text("December 2018")
//        }
//    }

}
