package com.guuguo.gank.app.gank.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.ielse.imagewatcher.ImageWatcher
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.guuguo.android.lib.extension.log
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.utils.systembar.SystemBarHelper
import com.guuguo.gank.R
import com.guuguo.gank.app.gank.adapter.GankWithCategoryAdapter
import com.guuguo.gank.app.gank.viewmodel.DateGankViewModel
import com.guuguo.gank.base.BaseViewModel
import com.guuguo.gank.base.MBaseActivity
import com.guuguo.gank.constant.MEIZI
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.databinding.ActivityGankBinding
import com.guuguo.gank.model.entity.GankModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.android.synthetic.main.toolbar_gank_detail.*
import netError
import java.util.concurrent.TimeUnit


class GankActivity : MBaseActivity<ActivityGankBinding>() {
    companion object {
        val TRANSLATE_GIRL_VIEW = "share_girl_image_view"
        fun intentTo(activity: Activity, image: View, meizi: GankModel) {
            val intent = Intent(activity, GankActivity::class.java)
            intent.putExtra(MEIZI, meizi)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, TRANSLATE_GIRL_VIEW)
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
        }
    }

    val viewModel by lazy { DateGankViewModel() }
    override fun getViewModel(): BaseViewModel? = viewModel

    val mGankAdapter by lazy {
        GankWithCategoryAdapter()
    }

    override fun getHeaderTitle() = mGankBean?.desc.safe()
    override fun getToolBar() = id_tool_bar
    override fun getLayoutResId() = R.layout.activity_gank

    var mGankBean: GankModel? = null


    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        viewModel.isError.observe(this, Observer {
            it?.log("错误了")
            it?.let {
                Snackbar.make(binding.containerView, it.netError().safe(), Snackbar.LENGTH_INDEFINITE).setAction("重试") {
                    viewModel.fetchDate(mGankBean!!.publishedAt!!)
                }.show()
            }
        })
        viewModel.gankDayLiveData.observe(this, Observer {
            mGankAdapter.setNewData(it)
        })
    }

    private var delayLoadingDispose: Disposable? = null  //延迟出现 如果网络很快就不出现了
    override fun loadingStatusChange(isRunning: Boolean) {
        "isRunning isRunning:$isRunning".log()
        if (isRunning) {
            delayLoadingDispose = Observable.timer(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progressbar.visibility = View.VISIBLE
                    }
        } else {
            progressbar.visibility = View.GONE;delayLoadingDispose?.dispose()
        }
    }

    override fun initView() {
        super.initView()
        initIvMeizi()
        initRecycler()
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchDate(mGankBean!!.publishedAt!!)
    }

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setHeightAndPadding(activity, getToolBar())
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mGankBean = intent.getSerializableExtra(MEIZI) as GankModel?
    }


    private fun initRecycler() {
        rv_gank.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        rv_gank.adapter = mGankAdapter
        mGankAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bean = mGankAdapter.getItem(position)!!
            if (!bean.isHeader)
                WebViewActivity.intentTo(bean.t, activity)
        }
    }

    override fun onBackPressedSupport() {
        if (iwHelper?.handleBackPressed() != true) {
            super.onBackPressedSupport()
            supportFinishAfterTransition()
        }
    }

    var iwHelper: ImageWatcherHelper? = null
    private fun initIvMeizi() {
        iv_head.setImageDrawable(OmeiziDrawable)
        iv_head.setOriginalSize(mGankBean!!.width, mGankBean!!.height)

        iv_head.setOnClickListener {
            val mapping = SparseArray<ImageView>() // 这个请自行理解，
            mapping.put(0, iv_head)
            val dataList = listOf(Uri.parse(mGankBean?.url))//被显示的图片们;
            iwHelper = ImageWatcherHelper.with(this, SimpleLoader())
            iwHelper?.show(iv_head, mapping, dataList)

        }

        ViewCompat.setTransitionName(iv_head, TRANSLATE_GIRL_VIEW)
    }

}

internal class SimpleLoader : ImageWatcher.Loader {
    override fun load(context: Context, uri: Uri, lc: ImageWatcher.LoadCallback) {
        Glide.with(context).load(uri)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(@NonNull resource: Drawable, @Nullable transition: Transition<in Drawable>?) {
                        lc.onResourceReady(resource)
                    }

                    override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {
                        lc.onLoadFailed(errorDrawable)
                    }

                    override fun onLoadStarted(@Nullable placeholder: Drawable?) {
                        lc.onLoadStarted(placeholder)
                    }
                })
    }
}