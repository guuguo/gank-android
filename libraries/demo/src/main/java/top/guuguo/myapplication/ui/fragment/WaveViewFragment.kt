package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.guuguo.android.lib.app.BaseCupertinoTitleActivity
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.widget.CustomMultiWaveView
import kotlinx.android.synthetic.main.fragment_waveview.*
import top.guuguo.myapplication.R

class WaveViewFragment : LBaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_waveview
    override fun getHeaderTitle() = "waveView"
    override fun getBackIconRes()=R.drawable.ic_arrow_back_24dp
    override fun initView() {
        super.initView()
        (activity as LBaseActivity).lightBar()
        wave_view.addWaves(arrayOf(
                CustomMultiWaveView.WaveBean(1000f, 4500, 48.dpToPx())
                , CustomMultiWaveView.WaveBean(1000f, 4200, 44.dpToPx())
                , CustomMultiWaveView.WaveBean(1000f, 3800, 40.dpToPx(), Color.WHITE)))
        wave_view.start()

        activity.let {
            if (it is BaseCupertinoTitleActivity) {
                it.getFunctionView()?.drawable = ContextCompat.getDrawable(activity, R.drawable.ic_search)
                it.getFunctionView()?.setOnClickListener {  }
            }
        }
        wave_view.setOnClickListener {
//            HomeGuideDialog(activity,wave_view, GuideType.TYPE_INPUT_WEIGHT).show()
        }
    }

    companion object {
        fun intentTo(activity: Activity) {
            LBaseActivity.intentTo(activity, WaveViewFragment::class.java, BaseCupertinoTitleActivity::class.java)
        }
    }
}