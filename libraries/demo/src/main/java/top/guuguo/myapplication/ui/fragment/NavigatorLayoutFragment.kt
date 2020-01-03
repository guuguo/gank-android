package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.viewanimator.ViewAnimator
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.getColorCompat
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.widget.SwipeNavigationLayout
import kotlinx.android.synthetic.main.fragment_banner2.*
import top.guuguo.myapplication.R
import top.guuguo.myapplication.ui.activity.BaseTitleActivity

class NavigatorLayoutFragment : LBaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_banner2

    override fun getHeaderTitle(): String? = "NavigatorLayout"

    companion object {
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, BaseTitleActivity::class.java)
            intent.putExtra(LBaseActivity.SIMPLE_ACTIVITY_INFO, NavigatorLayoutFragment::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    private var lastList: List<Int>? = null
    private var currentVideoIndex = 0

    override fun loadData() {
        super.loadData()
        val mList = mutableListOf(
                R.color.color_red_orange,
                R.color.color_yellow,
                R.color.color_blue)
        lastList = mList
        initBanner(lastList!![currentVideoIndex])
    }

    override fun initView() {
        super.initView()
        navigation.setNavigationListener(object : SwipeNavigationLayout.NavigationListener {
            override fun navigationNext() {
                lastList?.let {
                    currentVideoIndex++
                    if (currentVideoIndex >= it.size.safe())
                        currentVideoIndex = 0
                    initBanner(lastList!![currentVideoIndex])
                }
            }

            override fun navigationBack() {
                lastList?.let {
                    currentVideoIndex--
                    if (currentVideoIndex < 0)
                        currentVideoIndex = it.size - 1
                    initBanner(lastList!![currentVideoIndex])
                }
            }
        })
    }

    val glideRequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.load_img)
            .priority(Priority.HIGH)


    /**
     * 开始加载广告图片
     * @param newslist
     */
    private fun initBanner(colorRes: Int) {
        image_view.setBackgroundColor(activity.getColorCompat(colorRes))
//        ViewAnimator.animate(image_view).alpha(1f, 0f).duration(200).onStop {
//            image_view.setBackgroundColor(activity.getColorCompat(colorRes))
//            ViewAnimator.animate(image_view).alpha(0f, 1f).duration(500).start()
//        }.start()

    }


}