package top.guuguo.myapplication

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import android.view.View
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import top.guuguo.myapplication.databinding.DialogCustomWarningBinding


class WarningDialog : IWarningDialog {
    override fun setCustomContent(v: View): IWarningDialog {
        return this
    }

    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
    }

    lateinit var binding: DialogCustomWarningBinding
    override fun onCreateView(): View {
        val view = layoutInflater.inflate(R.layout.dialog_custom_warning, null)
        binding = DataBindingUtil.bind(view)!!
        widthRatio(0f)
        heightRatio(0f)
        dimEnabled(true)

        val lp = window?.attributes
        lp?.dimAmount = 0.5f
        window?.attributes = lp

        return view
    }

    override fun setUiBeforShow() {
        if (btnNum.get() == 0) {
            if (!btnText2.get().isNullOrEmpty())
                btnNum.set(2)
            else if (!btnText1.get().isNullOrEmpty())
                btnNum.set(1)
        }
        binding.warningDialog = this
        binding.btn1.setOnClickListener {
            btnClick1?.invoke(this)
        }
        binding.btn2.setOnClickListener {
            btnClick2?.invoke(this)
        }
    }


    private var isCanCancel = false


    var title = ObservableField("")
        private set
    var message = ObservableField("")
        private set
    var btnPosition = ObservableField(0)
        private set
    var btnNum = ObservableField(1)
        private set
    var btnText1 = ObservableField("")
        private set
    var btnText2 = ObservableField("")
        private set
    var btnClick1: ((v: WarningDialog) -> Unit)? = null
    var btnClick2: ((v: WarningDialog) -> Unit)? = null

    override fun title(title: String) = this.also { it.title.set(title) }
    override fun message(message: String) = this.also { it.message.set(message) }
    override fun btnNum(btnNum: Int) = this.also { it.btnNum.set(if (btnNum < 0) 0 else if (btnNum > 2) 2 else btnNum) }
    override fun btnText(vararg text: String) = this.also { text.getOrNull(0)?.apply { it.btnText1.set(this) }; text.getOrNull(1)?.apply { it.btnText2.set(this) } }
    override fun btnClick(vararg clicks: ((v: IWarningDialog) -> Unit)?) = this.also { clicks.getOrNull(0)?.apply { it.btnClick1 = this }; clicks.getOrNull(1)?.apply { it.btnClick2 = this } }
    override fun positiveBtnPosition(btnPosition: Int) = this.also { it.btnPosition.set(btnPosition) }

}//Fast Function
