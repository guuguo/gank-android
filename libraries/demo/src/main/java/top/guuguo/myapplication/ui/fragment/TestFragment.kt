package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import android.view.View
import com.guuguo.android.lib.app.BaseCupertinoTitleActivity
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.lib.extension.log
import com.guuguo.android.lib.extension.toast

import com.guuguo.android.lib.widget.FunctionTextView
import com.guuguo.android.lib.widget.simpleview.StateLayout
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_test.*
import top.guuguo.myapplication.R
import java.util.concurrent.TimeUnit

class TestFragment : LBaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_test
    override fun getHeaderTitle() = "testFragment"
    override fun getBackIconRes()=R.drawable.ic_arrow_back_24dp
    var type = 0
    override fun initView() {
        mActivity.findViewById<FunctionTextView>(R.id.tv_function)?.let {
            it.text="按钮"
           it.setOnClickListener { "按钮".toast() }
        }
        (mActivity as? LBaseActivity)?.darkBar()
        super.initView()
        search.searchClick = {
            doit()

        }
        search.onBackClick = {
            state.showEmpty("不行了", R.drawable.empty_cute_girl_box, null, null)
        }
        btn_theme.setOnClickListener {
            DialogFragment.intentTo(this)
        }
        btn_change.setOnClickListener {
            doit()
        }
    }

    fun delay(callback: () -> Unit) {
        Completable.complete()
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { callback() }
                .subscribe({}, {})
                .isDisposed
    }

    fun doit() {
        state.layoutRes = R.layout.widget_include_simple_empty_view1
        StateLayout.loadingDrawableClass = null
        state.showLoading("", true)
        when (type) {
            0 -> delay {
                if (!state.isLoading)
                    state.showLoading("", true)
                delay { state.restore() }
            }
            1 -> delay {
                state.showEmpty("不行了", R.drawable.empty_cute_girl_box)
            }
            2 -> delay {
                state.showError("网络异常，请稍候重试", "重试", listener = View.OnClickListener {
                    type = 2
                    doit()
                }, imgRes = 0)
            }
            3 -> delay {
                state.showCustomView(R.layout.dialog_custom_warning)
            }
        }
        type++
        if (type == 4)
            type = 0
    }

    companion object {
        fun intentTo(activity: Activity) {
            "dialog fragment".log()
            "dialog fragment 2".log("可怕")
            LBaseActivity.intentTo(activity, TestFragment::class.java, BaseCupertinoTitleActivity::class.java)
        }
    }
}