package com.guuguo.gank.app.fragment

//import com.guuguo.gank.app.fragment.SearchRevealFragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.guuguo.android.lib.extension.log
import com.guuguo.android.lib.extension.safe
import com.guuguo.gank.R
import com.guuguo.gank.R.id.container_view
import com.guuguo.gank.R.id.swiper
import com.guuguo.gank.app.activity.WebViewActivity
import com.guuguo.gank.app.adapter.GankAdapter
import com.guuguo.gank.app.fragment.GankCategoryContentFragment.Companion.GANK_TYPE_STAR
import com.guuguo.gank.base.BaseFragment
import com.guuguo.gank.constant.MEIZI_COUNT
import com.guuguo.gank.model.Ganks
import com.guuguo.gank.model.entity.GankModel
import com.guuguo.gank.net.ApiServer
import com.guuguo.gank.net.EmptyConsumer
import com.guuguo.gank.net.ErrorConsumer
import com.guuguo.gank.source.GankRepository
import com.just.agentweb.LogUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.view_refresh_recycler.*
import java.util.*


class GankCategoryContentFragment : BaseFragment() {

    var page = 1
    var gankAdapter = GankAdapter()
    var gank_type = "Android"

    companion object {
        const val ARG_GANK_TYPE = "ARG_GANK_TYPE"
        const val GANK_TYPE_STAR = "GANK_TYPE_STAR"
        fun newInstance(title: String): GankCategoryContentFragment {
            val fragment = GankCategoryContentFragment()
            val bundle = Bundle()
            bundle.putString(GankCategoryContentFragment.ARG_GANK_TYPE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_category_content;
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        gank_type = arguments!![ARG_GANK_TYPE] as String
    }

    override fun initView() {
        super.initView()
        initRecycler()
        activity.getToolBar()?.setOnClickListener { recycler.smoothScrollToPosition(0) }
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mFirstLazyLoad) {
            onRefresh()
        }else if(gank_type== GANK_TYPE_STAR){
            onRefresh()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFirstLazyLoad = true
    }

    private fun initRecycler() {
        gankAdapter.setOnLoadMoreListener({
            page++
            fetchGankData(page)
        }, recycler)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = gankAdapter
        gankAdapter.setOnItemClickListener { _, view, position ->
            val bean = gankAdapter.getItem(position)!!
            WebViewActivity.intentTo(bean, activity)
        }
    }

    private fun onRefresh() {
        page = 1
        fetchGankData(page)
    }

    private fun setLocalCommentList(meiziData: Ganks<ArrayList<GankModel>>) {
        meiziData.let {
            showMeiziList(meiziData.results!!)
            hideProgress()
        }
    }

    private fun fetchGankData(page: Int) {
        when (gank_type) {
            GANK_TYPE_STAR -> {
                GankRepository.getStarGanks()
                        .doOnSuccess (this::showMeiziList)
                        .doOnSuccess {
                            gankAdapter.loadMoreEnd()
                        }
                        .subscribe(EmptyConsumer(), ErrorConsumer())
            }
            else -> {
                ApiServer.getGankData(gank_type, MEIZI_COUNT, page)
                        .compose(bindToLifecycle())
                        .doOnError { throwable ->
                            ("initLocalCommentList doOnError: " + throwable.toString()).log()
                        }
                        .doOnNext(this::setLocalCommentList)
                        .subscribe(EmptyConsumer(), ErrorConsumer())

            }
        }
    }


    fun hideProgress() {
        dialogDismiss()
    }

    fun showErrorView(e: Throwable) {
        Toast.makeText(activity, e.message.safe(), Toast.LENGTH_LONG).show()
    }


    fun showMeiziList(lMeiziList: List<GankModel>) {
        if (lMeiziList.size < MEIZI_COUNT) {
            gankAdapter.loadMoreEnd(false)
        }
        if (page == 1) {
            gankAdapter.setNewData(lMeiziList)
        } else {
            gankAdapter.loadMoreComplete()
            gankAdapter.addData(lMeiziList)
        }
    }

    fun showTip(msg: String) {
        Snackbar.make(container_view, msg, Snackbar.LENGTH_SHORT)
    }
}

