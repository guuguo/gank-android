package com.guuguo.learnsave.app.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.guuguo.learnsave.R
import com.guuguo.learnsave.adapter.MeiziAdapter
import com.guuguo.learnsave.app.base.BaseActivity
import com.guuguo.learnsave.extension.safe
import com.guuguo.learnsave.extension.showSnackTip
import com.guuguo.learnsave.extension.updateData
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.presenter.MainPresenter
import com.guuguo.learnsave.util.DisplayUtil
import com.guuguo.learnsave.util.MEIZI
import com.guuguo.learnsave.util.OmeiziDrawable
import com.guuguo.learnsave.util.TRANSLATE_GIRL_VIEW
import com.guuguo.learnsave.view.IMainView
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.proguard.ac
import kotterknife.bindView
import java.io.Serializable
import java.util.*


class MainActivity : ToolBarActivity(), IMainView {
    var page = 1
    var isRefresh = false
    var meiziAdapter = MeiziAdapter()

    val contentView by bindView<View>(R.id.activity)
    val presenter: MainPresenter by lazy { MainPresenter(activity, this) }
    val recycler: RecyclerView by bindView(R.id.recycler)
    val swiper: SwipeRefreshLayout by  bindView(R.id.swiper)


    override fun getLayoutResId(): Int {
        return R.layout.activity_main;
    }

    override fun initPresenter() {
        presenter.init()
    }

    override fun initIView() {
        initSwiper()
        initRecycler()
        toolbar.setOnClickListener { recycler.smoothScrollToPosition(0) }

        swiper.post {
            swiper.isRefreshing = true
            onRefresh()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menu_check_up->Beta.checkUpgrade()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initRecycler() {
        meiziAdapter.openLoadMore(12)
        meiziAdapter.setOnLoadMoreListener {
            presenter.fetchMeiziData(page)
        }

        recycler.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recycler.adapter = meiziAdapter
        recycler.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun SimpleOnItemChildClick(p0: BaseQuickAdapter<*>?, view: View?, i: Int) {
                when (view!!.id) {
                    R.id.image -> {
                        val image = view as ImageView
                        OmeiziDrawable = view.getDrawable()
                        val intent = Intent(activity, GankActivity::class.java)
                        intent.putExtra(MEIZI, meiziAdapter.getItem(i) as Serializable)
                        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, TRANSLATE_GIRL_VIEW)
                        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
                    }
                }
            }
        })
    }

    private fun initSwiper() {
        swiper.setOnRefreshListener {
            onRefresh()
        };
    }

    private fun onRefresh() {
        page = 1
        isRefresh = true
        presenter.fetchMeiziData(page)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
        if (isRefresh) {
            swiper.isRefreshing = false
            isRefresh = false
        }
    }

    override fun showNoMoreData() {
        meiziAdapter.loadComplete()
    }

    override fun showErrorView(e: Throwable) {
        Toast.makeText(activity, e.message.safe(), Toast.LENGTH_LONG).show()
    }


    override fun showMeiziList(lMeiziList: List<GankModel>) {
        page++
        if (isRefresh) {
            meiziAdapter.updateData(lMeiziList)
        } else {
            recycler.post {
                meiziAdapter.addData(lMeiziList)
            }
        }
    }

    override fun showTip(msg: String) {
        showSnackTip(contentView, msg)
    }
}

