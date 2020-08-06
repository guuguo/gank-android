package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorRes
import android.view.View
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.lib.widget.roundview.RoundTextView
import kotlinx.android.synthetic.main.fragment_flowlayout.*
import top.guuguo.flowlayout.FlowAdapter
import top.guuguo.flowlayout.FlowLayout
import top.guuguo.myapplication.R
import top.guuguo.myapplication.ui.activity.BaseNoTitleActivity

class FlowLayoutFragment : LBaseFragment() {
    val bookStr = arrayOf("影视", "读书", "音乐")
    val lifeStr = arrayOf("旅行", "居家", "美食", "手作", "运动健身", "时尚")
    val worldStr = arrayOf("人文", "科技", "摄影", "艺术", "画画儿", "建筑")
    val growStr = arrayOf("故事", "情感", "成长", "涨知识", "理财")
    val interestStr = arrayOf("找乐", "宠物", "娱乐八卦", "动漫", "自然", "美女")
    override fun getLayoutResId() = R.layout.fragment_flowlayout
    override fun customHeaderType()=CustomHeader.LINEAR
    override fun getHeaderTitle()="flowayout"
    override fun initView() {
        super.initView()

        setAdapterViews(fl_no_check, bookStr, R.color.color_green)
        setAdapterViews(fl_single_check, lifeStr, R.color.color_yellow_dark)
        setAdapterViews(fl_multi_check, worldStr, R.color.color_green_dark)
        val multiCheck3Adapter = setAdapterViews(fl_multi_3, growStr, R.color.color_blue_dark)
        multiCheck3Adapter.setCheckLimit(3)
        val multiCheck5Adapter = setAdapterViews(fl_multi_5, interestStr, R.color.color_purple)
        multiCheck5Adapter.setCheckLimit(5)
    }

    private fun setAdapterViews(view: FlowLayout, strS: Array<String>, @ColorRes colorRes: Int): FlowAdapter<String> {
        val color = mActivity.getColorCompat(colorRes)
        val adapter = object : FlowAdapter<String>() {
            override fun onCreateView(): View {
                return mActivity.inflateLayout(R.layout.item_tag, fl_no_check, false);
            }

            override fun onBindView(view: View, item: String, isChecked: Boolean) {
                val tv = view.findViewById<RoundTextView>(R.id.tv_content)
                tv.text = item
                tv.delegate.strokeColor = color
                if (isChecked) {
                    tv.setTextColor(Color.WHITE)
                    tv.delegate.backgroundColor = color
                } else {
                    tv.setTextColor(color)
                    tv.delegate.backgroundColor = Color.TRANSPARENT
                }
            }

            override fun isMaxChecked(checkedType: Int) {
                super.isMaxChecked(checkedType)
                "最多只能选择${checkedType}个".toast()
            }
        }
        adapter.setNewData(strS.toList())
        view.setAdapter(adapter)
        return adapter
    }


    companion object {
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, BaseNoTitleActivity::class.java)
            intent.putExtra(LBaseActivity.SIMPLE_ACTIVITY_INFO, FlowLayoutFragment::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }
}
