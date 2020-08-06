package top.guuguo.myapplication.ui.fragment

import android.app.Activity
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.guuguo.android.dialog.dialog.CupertinoWarningDialog
import com.guuguo.android.dialog.dialog.CustomAlertDialog
import com.guuguo.android.dialog.dialog.DefaultWarningDialog
import com.guuguo.android.dialog.dialog.NewEditAlertDialog
import com.guuguo.android.dialog.utils.*
import com.guuguo.android.lib.app.*
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.log
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_dialog.*
import top.guuguo.myapplication.MyDialog
import top.guuguo.myapplication.R
import top.guuguo.myapplication.WarningDialog
import java.util.concurrent.TimeUnit

@Route(path = "/demo/dialog")
class DialogFragment : LBaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_dialog
    override fun getHeaderTitle() = "dialogFragment"
    override fun getBackIconRes() = R.drawable.ic_arrow_back_24dp
    override fun initView() {
        super.initView()
        mActivity.let {
            if (it is BaseCupertinoTitleActivity) {
                it.getFunctionView().drawable = mActivity.getDrawableCompat(R.drawable.ic_close_black_24dp)
                it.getFunctionView().setOnClickListener { }
            }

            (it as?LBaseActivity)?.apply {
                if (isLightTheme()) {
                    lightBar()
                } else {
                    darkBar()
                }
            }

        }

        btn_theme.setOnClickListener {
            if (DialogSettings.tip_theme == DialogSettings.THEME_LIGHT) {
                DialogSettings.tip_theme = DialogSettings.THEME_DARK
            } else {
                DialogSettings.tip_theme = DialogSettings.THEME_LIGHT
            }
        }
        btn_warning_type.setOnClickListener {
            when {
                DialogHelper.warningDialogClass.name == CupertinoWarningDialog::class.java.name -> DialogHelper.warningDialogClass = DefaultWarningDialog::class.java
                DialogHelper.warningDialogClass.name == DefaultWarningDialog::class.java.name -> DialogHelper.warningDialogClass = WarningDialog::class.java
                else -> DialogHelper.warningDialogClass = CupertinoWarningDialog::class.java
            }
        }
        btn_loading.setOnClickListener {
            mActivity.dialogLoadingShow("加载中")
            Completable.complete().delay(2, TimeUnit.SECONDS).subscribe {
                mActivity.dialogDismiss()
            }
        }
        btn_error.setOnClickListener { mActivity.dialogErrorShow("出错了") }
        btn_message.setOnClickListener { mActivity.dialogMsgShow("天气很好", "知道了", null) }
        btn_warning.setOnClickListener {
            mActivity.dialogWarningShow("确定继续吗", "取消", "确定")
        }
        btn_success.setOnClickListener { mActivity.dialogCompleteShow("可以了哈哈哈哈你好啊 啊啊 啊") }
        btn_alert_edit.setOnClickListener {
            NewEditAlertDialog(mActivity).paddingVertical(10.dpToPx()).title("填写").btnText("取消", "完成").btnClick({ it.dismiss() }, { it.dismiss() }).show()
        }
        btn_alert_custom.setOnClickListener {
            CustomAlertDialog(mActivity).contentView(TextView(mActivity)).title("填写").show()
        }
        btn_alert_custom2.setOnClickListener {
            MyDialog(mActivity).heightRatio(0.99f).widthRatio(0.9f).show()
        }
        btn_alert_custom3.setOnClickListener {
            MyDialog(mActivity).show()
        }
    }

    companion object {
        fun intentTo(activity: Activity) {
            LBaseActivity.intentTo(activity, DialogFragment::class.java, BaseCupertinoTitleActivity::class.java)
            "dialog fragment".log()
            "dialog fragment 2".log("可怕")
        }

        fun intentTo(fragment: androidx.fragment.app.Fragment) {
            fragment.startActivity(LBaseActivity.getIntent(fragment.activity!!, DialogFragment::class.java, BaseCupertinoTitleActivity::class.java, null))
            "dialog fragment".log()
            "dialog fragment 2".log("可怕")
        }
    }
}