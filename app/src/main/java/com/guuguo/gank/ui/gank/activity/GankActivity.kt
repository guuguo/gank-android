package com.guuguo.gank.ui.gank.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.ielse.imagewatcher.ImageWatcher
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.google.android.material.snackbar.Snackbar
import com.guuguo.android.lib.extension.log
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.gank.R
import com.guuguo.gank.ui.gank.adapter.GankWithCategoryAdapter
import com.guuguo.gank.ui.gank.viewmodel.DateGankViewModel
import com.guuguo.gank.base.BaseViewModel
import com.guuguo.gank.base.MBaseActivity
import com.guuguo.gank.constant.OmeiziDrawable
import com.guuguo.gank.constant.OmeiziDrawableStr
import com.guuguo.gank.databinding.ActivityGankBinding
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.netError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.android.synthetic.main.toolbar_gank_detail.*
import java.util.concurrent.TimeUnit


class GankActivity : MBaseActivity<ActivityGankBinding>() {
    companion object {
        const val TRANSLATE_GIRL_VIEW = "share_girl_image_view"
        const val ARG_GANK_POSITION = "ARG_GANK_POSITION"
        const val ARG_GANK_LIST = "ARG_GANK_LIST"

        fun intentTo(activity: Activity, image: View, meizi: ArrayList<GankModel>, position: Int) {
            val intent = Intent(activity, GankActivity::class.java)
            intent.putParcelableArrayListExtra(ARG_GANK_LIST, meizi)
            intent.putExtra(ARG_GANK_POSITION, position)
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, TRANSLATE_GIRL_VIEW)
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
        }
    }

    val viewModel by lazy { DateGankViewModel() }
    override fun getViewModel(): BaseViewModel? = viewModel

    private val mGankAdapter by lazy {
        GankWithCategoryAdapter()
    }

    override fun getHeaderTitle() = mGankBean?.desc.safe()
    override fun getToolBar() = id_tool_bar
    override fun getLayoutResId() = R.layout.activity_gank

    var mGankPosition = 0
    var mGankList: ArrayList<GankModel>? = null
    var mGankBean: GankModel? = null


    override fun initViewModelCallBack() {
        super.initViewModelCallBack()
        viewModel.isError.observe(this, Observer {
            it?.log("错误了")
            it?.let {
                Snackbar.make(binding.containerView, it.netError().safe(), com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE).setAction("重试") {
                    viewModel.fetchDate(mGankList?.getOrNull(mGankPosition)!!.publishedAt!!)
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

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mGankList = intent.getParcelableArrayListExtra<GankModel>(ARG_GANK_LIST)
        mGankPosition = intent.getIntExtra(ARG_GANK_POSITION, 0)
        mGankBean = mGankList?.getOrNull(mGankPosition)
    }

    override fun initView() {
        super.initView()
        animationFadOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        animationFadIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        initIvMeizi()
        initRecycler()
//        binding.navigation.setNavigationListener(object : SwipeNavigationLayout.NavigationListener {
//            override fun navigationBack() {
//                if (mGankPosition > 0) {
//                    mGankPosition--
//                    mGankBean = mGankList?.getOrNull(mGankPosition)
//
//                    animateLoad()
//                } else {
//                    "上面没有了".toast()
//                }
//            }
//
//            override fun navigationNext() {
//                if (mGankPosition < mGankList?.size.safe() - 1) {
//                    mGankPosition++
//                    mGankBean = mGankList?.getOrNull(mGankPosition)
//
//                    animateLoad()
//                } else {
//                    "下面去上一页加载吧".toast()
//                }
//            }
//        })
    }

    lateinit var animationFadOut: Animation
    lateinit var animationFadIn: Animation

    fun animateLoad() {
        animationFadOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                loadData()
                initIvMeizi()
                supportActionBar?.title = getHeaderTitle()
                animationFadIn.setAnimationListener(null)
                binding.containerView.startAnimation(animationFadIn)
            }
        })
        binding.containerView.startAnimation(animationFadOut)
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchDate(mGankBean!!.publishedAt!!)
    }

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setHeightAndPadding(activity, getToolBar())
    }


    private fun initRecycler() {
        rv_gank.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        rv_gank.adapter = mGankAdapter
        mGankAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bean = mGankAdapter.getItem(position)!!
            if (!bean.isHeader)
                WebViewActivity.intentTo(bean.t, activity)
        }
    }

    override fun onBackPressed() {
        if (iwHelper?.handleBackPressed() != true) {
            super.onBackPressed()
            supportFinishAfterTransition()
        }
    }

    var iwHelper: ImageWatcherHelper? = null
    private fun initIvMeizi() {
        mGankBean?.let {

            if (mGankBean!!.width > 0) {
                iv_head.setOriginalSize(mGankBean!!.width, mGankBean!!.height)
                if (!OmeiziDrawableStr.isNullOrEmpty() && OmeiziDrawableStr == mGankBean?.url && OmeiziDrawable.get() != null) {
                    iv_head.setImageDrawable(OmeiziDrawable.get())
                } else {
                    Glide.with(iv_head).load(mGankBean?.url).apply(RequestOptions().override(ImageViewTarget.SIZE_ORIGINAL, ImageViewTarget.SIZE_ORIGINAL)).into(iv_head)
                }
                ViewCompat.setTransitionName(iv_head, TRANSLATE_GIRL_VIEW)
            } else {
                Glide.with(iv_head).asBitmap()
                        .load(mGankBean?.url).apply(RequestOptions().override(ImageViewTarget.SIZE_ORIGINAL, ImageViewTarget.SIZE_ORIGINAL))
                        .listener(object : RequestListener<Bitmap> {
                            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                mGankBean?.width = resource!!.width
                                mGankBean?.height = resource.height
                                iv_head.setOriginalSize(mGankBean!!.width, mGankBean!!.height)
                                return false
                            }

                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                                return false
                            }

                        })
                        .into(iv_head)
            }

            iv_head.setOnClickListener {
                val mapping = SparseArray<ImageView>() // 这个请自行理解，
                mapping.put(mGankPosition, iv_head)
                val dataList = mGankList?.map { Uri.parse(it?.url) }//被显示的图片们;
                iwHelper = ImageWatcherHelper.with(this, SimpleLoader())
                iwHelper?.show(iv_head, mapping, dataList)
            }
        }
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