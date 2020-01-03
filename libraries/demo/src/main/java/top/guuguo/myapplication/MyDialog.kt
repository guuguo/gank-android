package top.guuguo.myapplication

import android.content.Context
import android.view.View
import com.guuguo.android.dialog.base.BaseDialog


class MyDialog(context: Context) : BaseDialog<MyDialog>(context) {
    override fun onCreateView(): View {
        val rootView = layoutInflater.inflate(R.layout.fragment_guild, null)
        return rootView
    }

    override fun setUiBeforShow() {
    }

    init {
        heightRatio(1f)
        widthRatio(1f)
    }
}//Fast Function
