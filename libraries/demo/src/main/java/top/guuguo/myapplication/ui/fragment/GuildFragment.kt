package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bianla.app.widget.dialog.HomeGuideDialog
import com.guuguo.android.lib.app.BaseCupertinoTitleActivity
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.widget.FunctionTextView
import top.guuguo.myapplication.R
import top.guuguo.myapplication.databinding.FragmentGuildBinding
import top.guuguo.myapplication.ui.guide.GuideType

class GuildFragment : LBaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_guild
    override fun getHeaderTitle() = "引导"
    override fun isNavigationBack() = true
    lateinit var binding: FragmentGuildBinding
    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        return super.setLayoutResId(inflater, resId, container).also { binding = DataBindingUtil.bind(it)!! }
    }

    override fun initView() {
        super.initView()
        mActivity.findViewById<FunctionTextView>(R.id.tv_function)?.let {
            it.setOnClickListener {
                HomeGuideDialog(mActivity, binding.btn, GuideType.TYPE_BIANLA_STORY).show()
                HomeGuideDialog(mActivity, (mActivity as BaseCupertinoTitleActivity).getFunctionView(), GuideType.TYPE_SWIPE_DELETE).show()
            }
        }
        mActivity.let {
            if (it is BaseCupertinoTitleActivity) {
                it.getFunctionView().drawable = ContextCompat.getDrawable(mActivity, R.drawable.ic_search)
            }
        }
    }

    companion object {
        fun intentTo(activity: Activity) {
            LBaseActivity.intentTo(activity, GuildFragment::class.java, BaseCupertinoTitleActivity::class.java)
        }
    }
}