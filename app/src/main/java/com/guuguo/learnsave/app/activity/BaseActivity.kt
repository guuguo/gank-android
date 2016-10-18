package com.guuguo.learnsave.app.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.guuguo.learnsave.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected var myApplication = MyApplication.instance
    protected var activity = this;

    abstract fun getLayoutResId(): Int
    abstract fun initPresenter()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutResId())
        ButterKnife.bind(this)
        initPresenter()
    }


}
