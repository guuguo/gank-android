package com.guuguo.gank.ui.gank.activity;

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.guuguo.android.lib.extension.formatDecimal
import com.guuguo.android.lib.extension.formatTime
import com.guuguo.android.lib.utils.FileUtil
import com.guuguo.android.lib.widget.roundview.RoundTextView
import com.guuguo.gank.R
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask

fun Long.getFitSize(byte2FitMemorySize: Int = 1): String = FileUtil.byte2FitMemorySize(this, byte2FitMemorySize)
/**
 * 更新弹窗demo
 */
class UpgradeActivity : Activity() {
    private var tv_title: TextView? = null
    private var tv_info: TextView? = null
    private var tv_feature: TextView? = null
    private var btnStart: RoundTextView? = null
    private var btnCancel: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.upgrade_dialog_upgrade)
        tv_title = getView("beta_title")
        tv_info = getView("beta_upgrade_info")
        tv_feature = getView("beta_upgrade_feature")
        btnStart = getView("beta_confirm_button")
        btnCancel = getView("beta_cancel_button")

        /*获取下载任务，初始化界面信息*/
        updateBtn(Beta.getStrategyTask())

        /*获取策略信息，初始化界面信息*/
        tv_title!!.text = Beta.getUpgradeInfo().title
        tv_info!!.text = StringBuilder().append("版本:${Beta.getUpgradeInfo().versionName}")
                .append("\n")
                .append("大小:${Beta.getUpgradeInfo().fileSize.getFitSize()}")
                .append("\n")
                .append("更新时间:${Beta.getUpgradeInfo().publishTime.formatTime("yyyy-MM-dd HH:mm")}")
        tv_feature!!.text = Beta.getUpgradeInfo().newFeature

        /*为下载按钮设置监听*/
        btnStart!!.setOnClickListener {
            val task = Beta.startDownload()
            updateBtn(task)
        }

        /*为取消按钮设置监听*/
        btnCancel?.setOnClickListener {
            finish();
        }

        /*注册下载监听，监听下载事件*/
        Beta.registerDownloadListener(object : DownloadListener {
            override fun onReceive(task: DownloadTask) {
                updateBtn(task)
            }

            override fun onCompleted(task: DownloadTask) {
                updateBtn(task)
            }

            override fun onFailed(task: DownloadTask, code: Int, extMsg: String) {
                updateBtn(task)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onStop() {
        super.onStop()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()

        /*注销下载监听*/
        Beta.unregisterDownloadListener()
    }

    fun updateBtn(task: DownloadTask) {

        /*根据下载任务状态设置按钮*/
        btnStart!!.text = when (task.status) {
            DownloadTask.INIT, DownloadTask.DELETED -> {
                "开始更新"
            }
            DownloadTask.COMPLETE -> {
                "安装"
            }
            DownloadTask.DOWNLOADING -> {
                "${(task.savedLength * 100.toDouble() / Beta.getUpgradeInfo().fileSize).formatDecimal(1)}%"
            }
            DownloadTask.PAUSED -> {
                "继续"
            }
            DownloadTask.FAILED -> {
                "失败(重来)"
            }
            else -> "开始更新"
        }
    }


    fun <T : View> getView(tag: String): T = window.decorView.findViewWithTag<View>(tag) as T

    fun <T : View> getView(id: Int): T = findViewById<View>(id) as T
}
