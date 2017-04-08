/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package top.guuguo.ganktv

import android.app.Fragment
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.*
import android.support.v17.leanback.widget.*
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget

import com.google.gson.Gson
import com.guuguo.android.lib.utils.DisplayUtil
import com.guuguo.ganktv.app.MEIZI_COUNT
import com.guuguo.learnsave.extension.getScreeHeight
import com.guuguo.learnsave.extension.getScreeWidth
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.model.retrofit.ApiServer
import io.reactivex.functions.Consumer
import java.net.URI
import java.util.*

/**
 * An example how to use leanback's [VerticalGridFragment].
 */
class GankMeiziGridFragment : VerticalGridFragment(), BrowseFragment.MainFragmentAdapterProvider {
    var mMainFragmentAdapter: BrowseFragment.MainFragmentAdapter<Fragment>? = null

    private var mDefaultBackground: Drawable? = null
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundURI: URI? = null
    private val mHandler = Handler()

    private val BACKGROUND_UPDATE_DELAY = 300
    private var mBackgroundManager: BackgroundManager? = null

    override fun getMainFragmentAdapter(): BrowseFragment.MainFragmentAdapter<Fragment> {
        if (mMainFragmentAdapter == null) {
            mMainFragmentAdapter = BrowseFragment.MainFragmentAdapter<Fragment>(this)
        }
        return mMainFragmentAdapter!!
    }

    private var mAdapter: ArrayObjectAdapter? = null
    var page = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.browse_title) // Badge, when set, takes precedent
        prepareBackgroundManager()
        setupRowAdapter()
        setupEventListeners()
    }

    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
    }

    private fun setupRowAdapter() {
        val gridPresenter = VerticalGridPresenter(ZOOM_FACTOR)
        gridPresenter.numberOfColumns = COLUMNS
        setGridPresenter(gridPresenter)

        val cardPresenter = CardPresenter()
        mAdapter = ArrayObjectAdapter(cardPresenter)
        adapter = mAdapter


        prepareEntranceTransition()
        Handler().postDelayed({
            loadRows()
            startEntranceTransition()
        }, 1000)
    }

    private fun loadRows() {


        ApiServer.getGankData(ApiServer.TYPE_FULI, MEIZI_COUNT, page)
                .subscribe(object : Consumer<Ganks> {
                    override fun accept(meiziData: Ganks?) {
                        meiziData?.let {
                            mAdapter!!.addAll(0, meiziData.results)
                        }
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(error: Throwable) {
                    }
                })

//        val gridHeader = HeaderItem(1, "分类")
//
//        val mGridPresenter = GridItemPresenter()
//        val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
//        gridRowAdapter.add("安卓")
//        gridRowAdapter.add("IOS")
//        gridRowAdapter.add("前端")
//
//        mRowsAdapter!!.add(ListRow(gridHeader, gridRowAdapter))
//
//        adapter = mRowsAdapter

    }

    //
//    private fun createRows() {
//        val json = Utils.inputStreamToString(resources
//                .openRawResource(R.raw.grid_example))
//        val row = Gson().fromJson<CardRow>(json, CardRow::class.java!!)
//        mAdapter!!.addAll(0, row.getCards())
//    }
    protected fun updateBackground(uri: String) {

        val width = activity.getScreeWidth()
        val height = activity.getScreeHeight()
        Glide.with(activity)
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into<SimpleTarget<GlideDrawable>>(object : SimpleTarget<GlideDrawable>(width, height) {
                    override fun onResourceReady(resource: GlideDrawable,
                                                 glideAnimation: GlideAnimation<in GlideDrawable>) {
                        mBackgroundManager!!.drawable = resource
                    }
                })
        mBackgroundTimer!!.cancel()
    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
        setOnItemViewSelectedListener(ItemViewSelectedListener())
    }

    private fun startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer!!.cancel()
        }
        mBackgroundTimer = Timer()
        mBackgroundTimer!!.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                    rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
            if (item is GankModel) {
                mBackgroundURI = URI(item.url)
                startBackgroundTimer()
            }
        }
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                   rowViewHolder: RowPresenter.ViewHolder?, row: Row) {

            if (item is GankModel) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.GANK, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        (itemViewHolder?.view as ImageCardView).mainImageView, DetailsActivity.SHARED_ELEMENT_NAME).toBundle()
                activity.startActivity(intent, bundle)
            } else if (item is String) {
                if (item.indexOf(getString(R.string.error_fragment)) >= 0) {
                    val intent = Intent(activity, BrowseErrorActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity, item, Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post {
                if (mBackgroundURI != null) {
                    updateBackground(mBackgroundURI!!.toString())
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mBackgroundTimer) {
            mBackgroundTimer!!.cancel()
        }
    }

    companion object {

        private val COLUMNS = 4
        private val ZOOM_FACTOR = FocusHighlight.ZOOM_FACTOR_MEDIUM
    }
}
