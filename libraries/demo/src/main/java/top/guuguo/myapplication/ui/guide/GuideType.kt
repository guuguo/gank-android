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
    TYPE_STUDENTS_AUDIT("点击这里管理新增学员～",{
        padding = 3.dpToPx()
        targetShape = 1
    }),
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
    //品牌故事
    TYPE_BIANLA_STORY("将变啦品牌故事分享到朋友圈吧～"),
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