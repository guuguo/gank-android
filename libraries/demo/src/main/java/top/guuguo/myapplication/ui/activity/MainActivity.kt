package top.guuguo.myapplication.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.extension.doAvoidDouble
import kotlinx.android.synthetic.main.activity_main.*
import top.guuguo.myapplication.R
import top.guuguo.myapplication.ThemeUtils
import top.guuguo.myapplication.ui.fragment.*

class MainActivity : LBaseActivity() {
    override fun getLayoutResId() = R.layout.activity_main
    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(activity)
    }

    override fun initView() {
        super.initView()
        v_theme.setOnClickListener {
            it.doAvoidDouble {
                ThemeUtils.changeToTheme(activity)
//                ThemeUtils.sThemeDark = !ThemeUtils.sThemeDark
//                if (ThemeUtils.sThemeDark) {
//                    activity.setTheme(R.style.MyTheme_Dark)
//                    theme.applyStyle(R.style.MyTheme_Dark, true)
//                } else {
//                    activity.setTheme(R.style.MyTheme_Light)
//                    theme.applyStyle(R.style.MyTheme_Light, true)
//                }
            }
        }
        v_dialog.setOnClickListener {
            it.doAvoidDouble {
                //                DialogFragment.intentTo(activity)
                ArouterActivity.intentToArouterPath(activity, "/demo/dialog", BaseTitleActivity::class.java)
            }
        }
        v_progress.setOnClickListener {
            it.doAvoidDouble {
                //                ProgressActivity.intentTo(activity)
                ARouter.getInstance().build("/test/progress").withString("name", "哈哈哈").navigation(activity)
            }
        }
        v_swip.setOnClickListener {
            it.doAvoidDouble {
                NavigatorLayoutFragment.intentTo(activity)
            }
        }
        v_flowlayout.setOnClickListener {
            it.doAvoidDouble {
                FlowLayoutFragment.intentTo(activity)
            }
        }
        v_wave.setOnClickListener {
            it.doAvoidDouble {
                WaveViewFragment.intentTo(activity)
            }
        }
        v_test.setOnClickListener {
            it.doAvoidDouble {
                TestFragment.intentTo(activity)
            }
        }
        v_guild.setOnClickListener {
            it.doAvoidDouble {
                GuildFragment.intentTo(activity)
            }
        }
//        v_state.setOnClickListener {
//            it.doAvoidDouble {
//                StateFragment.intentTo(activity)
//            }
//        }
    }
}
