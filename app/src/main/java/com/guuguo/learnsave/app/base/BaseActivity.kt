package com.guuguo.learnsave.app.base

import android.os.Bundle
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        ButterKnife.bind(this)
        initPresenter() }

}
