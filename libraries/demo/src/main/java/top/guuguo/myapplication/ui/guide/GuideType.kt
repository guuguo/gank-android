package top.guuguo.myapplication.ui.guide;

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import com.bianla.app.widget.dialog.HomeGuideDialog
import com.github.florent37.viewanimator.ViewAnimator
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.dpToPxF
import top.guuguo.myapplication.MyApplication
import top.guuguo.myapplication.R

enum class GuideType(var text: String, var beforeShow: HomeGuideDialog.() -> Unit = {}) : IGuideType {
    TYPE_EVALUATE("每天快速上秤"), //上称
    TYPE_V_CERTIFICATION("管理师和V认证抢单都在这里呦～"), //v 认证
    TYPE_STUDENTS_AUDIT("点击这里管理新增学员～",{
        padding = 3.dpToPx()
        targetShape = 1
    }),
    TYPE_HEALTH_REPORT("来这里查看你的身体健康数据呦～"),
    TYPE_EXCLUSIVE_COACH_SEARCH("快来这里找到专属管理师吧～"), //管理师
    TYPE_EXCLUSIVE_COACH_TALK("您的专属管理师就在这里呦~，赶快联系吧～"), //管理师
    ///糖化测验
    TYPE_SACCHARIFICATION_EXAM("新增功能，可以检测身体的糖化损伤呦～",{
        anchorMargin = (-20).dpToPx()
    }),
    TYPE_SWIPE_DELETE("左滑可删除此条消息～",{
        setUpCustomView = { rect, parent ->
            val swipe = ImageView(parent.context).apply {
                layoutParams = FrameLayout.LayoutParams(135.dpToPx(), 40.dpToPx(), Gravity.END).apply {
                    topMargin = (rect.top + rect.bottom) / 2 - 20.dpToPx()
                    rightMargin = (-135).dpToPx()
                }
            }
            swipe.setImageResource(R.drawable.home_guide_swipe_delete)
            parent.addView(swipe)

            val swipeHand = ImageView(parent.context).apply {
                layoutParams = FrameLayout.LayoutParams(58.dpToPx(), 67.dpToPx(), Gravity.END).apply {
                    topMargin = (rect.top + rect.bottom) / 2 + 10.dpToPx()
                    rightMargin = (-40).dpToPx()
                }
            }
            swipeHand.setImageResource(R.drawable.home_guide_swipe_delete_hand)
            parent.addView(swipeHand)


            val animator = ViewAnimator.animate(swipeHand)
                    .translationX((30).dpToPxF(), (-105).dpToPxF())
                    .alpha(1f, 1f, 0f)
                    .duration(2000)
                    .repeatCount(500)

                    .andAnimate(swipe)
//                                    .width(0f, 135.dpToPxF())
                    .translationX(0f, (-135).dpToPxF())
                    .alpha(1f, 1f, 0f)
                    .duration(2000)
                    .repeatCount(500)
                    .decelerate().apply { start() }

            setOnDismissListener { animator.cancel() }
        }
    }),
    ///服务中心减脂前,步曲引导
    TYPE_BEFORE_SERVE_SIX_SIX_SIX("新用户完成6步曲，享受管理师定制化的\n减脂服务哟～"),
    //减脂中--用户身体数据
    TYPE_IN_SERVE_HEALTH_AGGREGATION("来这里记录下自己的身体数据吧～",{
        padding = -6.dpToPx()
    }),
    //减脂中--管理师制定的每日方案
    TYPE_LOOKING_COACH_PROJECT("点击查看管理师制定的每日方案", {
        padding = 3.dpToPx();
    }),
    //隐私设置
    TYPE_SETTING_PRIVACY("在这里设置是否让别人看你的\n减脂数据哦～"),
    //减脂前--上秤开始减脂
    TYPE_UP_SCALE_TO_HERE("从这里开始上秤了解身体数据～",{
        padding = 3.dpToPx()
    }),
    //首页服务
    TYPE_HOME_SERVICE("在这里体验科学健康的减脂服务"),
    //切换管理师引导
    TYPE_CHECKOUT_COACH_PAGE("一键切换到管理师身份的页面～"),
    //品牌故事
    TYPE_BIANLA_STORY("将变啦品牌故事分享到朋友圈吧～"),
    //管理师帮忙减脂
    TYPE_COACH_HELP_REDUCE("点击查看帮助学员取得的成绩吧～"),
    //专属减脂管理师
    TYPE_HOME_SERVICE_COACH("点击这里咨询您的专属减脂管理师吧～"),
    //糖负荷计算器
    TYPE_CALCULATE("糖负荷检测可以知道食物 GL值，让你吃的更健康❌"),
    ;


    companion object {
        private val prefs by lazy { MyApplication.instance.getSharedPreferences("Dialog", Context.MODE_PRIVATE) }
    }
    fun <U> SharedPreferences.findPreference(name: String, default: U): U = with(this) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> {
                throw IllegalArgumentException("not support type ${default.toString()}")
            }
        }
        return@with res as U
    }

    fun <T> SharedPreferences.putPreference(name: String, value: T) = with(this.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> {
                throw IllegalArgumentException("not support type ${value.toString()}")
            }
        }.apply()
    }

    override fun hasShow() = prefs.findPreference(name, false)
    override fun hasShow(show: Boolean) = prefs.putPreference(name, show)
    override fun getTextStr(): String = text
    override fun beforeShow(dialog: HomeGuideDialog) {
        dialog.beforeShow()
    }
}

interface IGuideType {
    fun getTextStr(): String
    /**是否展示过*/
    fun hasShow(): Boolean

    fun hasShow(show: Boolean = false)
    fun beforeShow(dialog: HomeGuideDialog)

}