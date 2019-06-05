package com.guuguo.gank.app.gank.fragment

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.guuguo.android.dialog.dialog.NormalListDialog
import com.guuguo.android.dialog.utils.OnOperItemClickL
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.utils.DisplayUtil
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.activity.AboutActivity
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.databinding.FragmentHomeBinding
import com.guuguo.gank.util.ThemeUtils
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.base_toolbar_common.*
import kotlinx.android.synthetic.main.base_toolbar_common.view.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(), Toolbar.OnMenuItemClickListener {
    override fun isNavigationBack() = false

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getHeaderTitle() = "Gank"
    override fun getMenuResId() = R.menu.main_menu
    override fun getToolBar() = contentView?.findViewById<Toolbar>(R.id.id_tool_bar)
    override fun getLayoutResId() = R.layout.fragment_home

    override fun setTitle(title: String) {
        tv_title.text = title
    }

    lateinit var mNavHostFragment: NavHostFragment

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_check_up -> Beta.checkUpgrade()
            R.id.menu_search -> {
//                val nav = NavOptions.Builder().setPopUpTo(R.id.searchFragment, true).build()
//                findNavController().navigate(R.id.action_to_search, null, nav)
                SearchActivity.intentTo(activity, binding.toolbar.searchCard)
            }
            R.id.menu_about -> AboutActivity.intentTo(activity)
            else -> return false
        }
        return true
    }

    override fun initView() {
        super.initView()
        binding.toolbar.imageView2.setOnClickListener {
            NormalListDialog(activity, arrayOf("黑夜", "关于"))
                .apply {
                    setOnOperItemClickL { parent, view, position, id ->
                        when (position) {
                            0 -> ThemeUtils.changeToTheme(activity)
                            1 -> AboutActivity.intentTo(activity)
                        }
                    }
                }.show()
        }
//        binding.toolbar.tvTitle.setOnClickListener {  }
//        SystemBarHelper.setPadding(activity, binding.toolbar.ll_bar)
        mNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//        NavigationUI.setupWithNavController(binding.navigation, mNavHostFragment.navController)
//        ViewCompat.setElevation(binding.toolbar, 8.dpToPx().toFloat())
        binding.toolbar.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(t: TabLayout.Tab?) {
                revealColor(t)
                val id = when (t?.position) {
                    0 -> R.id.dailyFragment
                    1 -> R.id.gankCategoryFragment
                    2 -> R.id.gankCategoryContentFragment
                    else -> R.id.dailyFragment
                }
//                when (t?.position) {
//                    1 -> ViewCompat.setElevation(binding.toolbar, 0f)
//                    else -> ViewCompat.setElevation(binding.toolbar, 8.dpToPx().toFloat())
//                }
                onNavDestinationSelected(id, mNavHostFragment.navController, true)
            }
        })
        getToolBar()?.inflateMenu(getMenuResId())
        getToolBar()?.setOnMenuItemClickListener(this)
        binding.toolbar.tvTitle.setOnClickListener {
            //            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, binding.toolbar.search_card, "share_search")
//            findNavController().navigate(R.id.action_to_search)
            SearchActivity.intentTo(activity, binding.toolbar.searchCard)
//            FragmentNavigator(activity,childFragmentManager,)
        }
    }

    /**执行 reveal 动画*/
    private fun revealColor(t: TabLayout.Tab?) {
        val color = when (t?.position) {
            0 -> activity.getColorCompat(R.color.colorPrimary)
            1 -> activity.getColorCompat(R.color.color_red_ccfa3c55)
            2 -> activity.getColorCompat(R.color.colorPrimaryBlue)
            else -> activity.getColorCompat(R.color.colorPrimary)
        }
        //reveal动画
        val location = IntArray(2) //view的位置
        val view = t?.view as View
        view?.getLocationInWindow(location)
        val radius = if (DisplayUtil.getScreenWidth() / 2 > location[0]) {
            DisplayUtil.getScreenWidth() - location[0]
        } else {
            location[0]
        }
        binding.toolbar.vBarRevealColor.setBackgroundColor(color)
        val sysbar = activity.findViewById<View?>(R.id.systembar_statusbar_view)
        val sysbarForeground = activity.findViewById<View?>(R.id.systembar_foreground_view)
        sysbar?.setBackgroundColor(color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim = ViewAnimationUtils.createCircularReveal(
                binding.toolbar.vBarRevealColor,
                location[0] + view.width.safe() / 2,
                location[1] + view.height.safe() + 20.dpToPx(),
                20.dpToPx().toFloat(),
                radius.toFloat()
            )
            val anim2 = ViewAnimationUtils.createCircularReveal(
                sysbar,
                location[0] + view.width.safe() / 2,
                location[1] + view.height.safe() + 20.dpToPx(),
                20.dpToPx().toFloat(),
                radius.toFloat()
            )
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.toolbar.root.setBackgroundColor(color)
                    sysbarForeground?.setBackgroundColor(color)
                }
            })
            anim.start()
            anim2.start()
        } else {
        }
    }

    internal fun onNavDestinationSelected(id: Int, navController: NavController, popUp: Boolean): Boolean {
        try {
            navController.popBackStack()
            navController.navigate(id)
            return true
        } catch (var6: IllegalArgumentException) {
            return false
        }

    }
}
