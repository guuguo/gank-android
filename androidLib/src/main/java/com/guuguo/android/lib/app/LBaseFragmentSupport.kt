package com.guuguo.android.lib.app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.*
import com.guuguo.android.R
import com.guuguo.android.dialog.dialog.TipDialog
import com.guuguo.android.dialog.dialog.base.IWarningDialog
import com.guuguo.android.dialog.utils.DialogHelper
import com.guuguo.android.lib.extension.initNav
import com.guuguo.android.lib.extension.safe
import com.trello.rxlifecycle2.android.FragmentEvent


/**
 * Created by guodeqing on 16/5/31.
 */
abstract class LBaseFragmentSupport : SupportFragment(), IView<FragmentEvent> {

    protected val TAG = this.javaClass.simpleName
    override lateinit var activity: LBaseActivitySupport
    protected abstract fun getLayoutResId(): Int

    private var isPrepare = false
    var mFirstLazyLoad = true
    protected var contentView: View? = null

    open protected fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        return inflater!!.inflate(resId, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.activity = context as LBaseActivitySupport
    }

    protected fun init(view: View) {
        activity = getActivity() as LBaseActivitySupport
        initToolbar()
        initView()
        loadData()
        //如果准备好 懒加载
        isPrepare = true
        if (userVisibleHint && !isHidden) {
            lazyLoad()
            mFirstLazyLoad = false
        }
    }


    /*toolbar*/
    open fun getBackIconRes(): Int = 0

    open fun getToolBar(): Toolbar? = null //fragment有自己的toolbar就重写该方法。fragment修改toolbar用activity.getSupportActionBar
    open protected fun initToolbar() {
        if (isNavigationBack()) {
            getToolBar()?.initNav(activity, getBackIconRes().safe(R.drawable.ic_arrow_back_white_24dp))
        }
        val str = getHeaderTitle()
        if (str != null)
            setTitle(str)
    }

    /*init*/

    override fun loadData() {}
    protected open fun initVariable(savedInstanceState: Bundle?) {}
    protected open fun initView() {}
    protected open fun getMenuResId() = 0
    open fun isNavigationBack() = true

    open fun overridePendingTransition() =Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        initVariable(savedInstanceState)
        if (contentView == null) {
            contentView = setLayoutResId(inflater, getLayoutResId(), container)
        }
        return contentView
    }
    /*menu and title*/

    open protected fun getHeaderTitle(): String? = null
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (getMenuResId() != 0)
            inflater!!.inflate(getMenuResId(), menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    protected open fun setTitle(title: String) {
        activity.title = title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    //如果准备好并可见,而且没有懒加载过 懒加载
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isPrepare) {
            lazyLoad()
            mFirstLazyLoad = false
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isPrepare) {
            lazyLoad()
            mFirstLazyLoad = false
        }
    }

    open fun lazyLoad() {
        activity.mFragment = this
    }


    open val isFullScreen: Boolean
        get() = false

    override fun dialogLoadingShow(msg: String, canTouchCancel: Boolean, maxDelay: Long, listener: DialogInterface.OnDismissListener?) {
        DialogHelper.dialogLoadingShow(activity, msg, canTouchCancel, maxDelay, listener)
    }

    override fun dialogErrorShow(msg: String, listener: DialogInterface.OnDismissListener?, delayTime: Int) {
        DialogHelper.dialogStateShow(activity, msg, listener, TipDialog.STATE_STYLE.error, delayTime.toLong())
    }

    override fun dialogCompleteShow(msg: String, listener: DialogInterface.OnDismissListener?, delayTime: Int) {
        DialogHelper.dialogStateShow(activity, msg, listener, TipDialog.STATE_STYLE.success, delayTime.toLong())
    }

    override fun dialogMsgShow(msg: String, btnText: String, listener: (() -> Unit)?): IWarningDialog? {
        return DialogHelper.dialogMsgShow(activity, msg, btnText, listener)
    }

    override fun dialogWarningShow(msg: String, cancelStr: String, confirmStr: String, listener: (() -> Unit)?): IWarningDialog? {
        return DialogHelper.dialogWarningShow(activity, msg, cancelStr, confirmStr, listener)
    }

    override fun showDialogOnMain(dialog: Dialog) {
        DialogHelper.showDialogOnMain(activity, dialog)
    }

    override fun dialogDismiss() {
        DialogHelper.dialogDismiss()
    }
}