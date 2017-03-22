package com.guuguo.learnsave.app.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.guuguo.learnsave.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment : Fragment() {
    private var unbinder: Unbinder? = null

    protected var myApplication = MyApplication.instance
    protected var contentView: View? = null
    protected var activity: BaseActivity? = null

    abstract fun initPresenter()
    abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as BaseActivity;
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        if (contentView == null) {
            contentView = inflater?.inflate(getLayoutResId(), container, false)
        }
        return contentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        unbinder = ButterKnife.bind(contentView!!)
        initPresenter()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }


}
