/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.DetailsFragment
import android.support.v17.leanback.widget.Action
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.ClassPresenterSelector
import android.support.v17.leanback.widget.DetailsOverviewRow
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import android.support.v17.leanback.widget.OnActionClickedListener
import android.support.v17.leanback.widget.OnItemViewClickedListener
import android.support.v17.leanback.widget.Presenter
import android.support.v17.leanback.widget.Row
import android.support.v17.leanback.widget.RowPresenter
import android.support.v4.app.ActivityOptionsCompat
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.guuguo.gank.model.entity.GankModel

import java.util.Collections

/*
 * LeanbackDetailsFragment extends DetailsFragment, a Wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its meta plus related videos.
 */
class GankDetailsFragment : DetailsFragment() {

    private var mSelectedGank: GankModel? = null

    private var mAdapter: ArrayObjectAdapter? = null
    private var mPresenterSelector: ClassPresenterSelector? = null

    private var mBackgroundManager: BackgroundManager? = null
    private var mDefaultBackground: Drawable? = null
    private var mMetrics: DisplayMetrics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate DetailsFragment")
        super.onCreate(savedInstanceState)

        prepareBackgroundManager()

        mSelectedGank = activity.intent
                .getSerializableExtra(DetailsActivity.GANK) as GankModel
        if (mSelectedGank != null) {
            setupAdapter()
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            updateBackground(mSelectedGank!!.url)
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager!!.attach(activity.window)
        mDefaultBackground = resources.getDrawable(R.drawable.default_background)
        mMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    protected fun updateBackground(uri: String) {
        Glide.with(activity)
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into<SimpleTarget<GlideDrawable>>(object : SimpleTarget<GlideDrawable>(mMetrics!!.widthPixels, mMetrics!!.heightPixels) {
                    override fun onResourceReady(resource: GlideDrawable,
                                                 glideAnimation: GlideAnimation<in GlideDrawable>) {
                        mBackgroundManager!!.drawable = resource
                    }
                })
    }

    private fun setupAdapter() {
        mPresenterSelector = ClassPresenterSelector()
        mAdapter = ArrayObjectAdapter(mPresenterSelector!!)
        adapter = mAdapter!!
    }

    private fun setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelectedGank.toString())
        val row = DetailsOverviewRow(mSelectedGank)
        row.imageDrawable = resources.getDrawable(R.drawable.default_background)
        val width = Utils.convertDpToPixel(activity
                .applicationContext, DETAIL_THUMB_WIDTH)
        val height = Utils.convertDpToPixel(activity
                .applicationContext, DETAIL_THUMB_HEIGHT)
        Glide.with(activity)
                .load(mSelectedGank?.url)
                .centerCrop()
                .error(R.drawable.default_background)
                .into(object : SimpleTarget<GlideDrawable>(width, height) {
                    override fun onResourceReady(resource: GlideDrawable,
                                                 glideAnimation: GlideAnimation<in GlideDrawable>) {
                        Log.d(TAG, "details overview card image url ready: " + resource)
                        row.imageDrawable = resource
                        mAdapter!!.notifyArrayItemRangeChanged(0, mAdapter!!.size())
                    }
                })

        row.addAction(Action(ACTION_WATCH_TRAILER.toLong(), resources.getString(
                R.string.watch_trailer_1), resources.getString(R.string.watch_trailer_2)))
        row.addAction(Action(ACTION_RENT.toLong(), resources.getString(R.string.rent_1),
                resources.getString(R.string.rent_2)))
        row.addAction(Action(ACTION_BUY.toLong(), resources.getString(R.string.buy_1),
                resources.getString(R.string.buy_2)))

        mAdapter!!.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background and style.
        val detailsPresenter = DetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor = resources.getColor(R.color.colorPrimary)
        detailsPresenter.isStyleLarge = true

        // Hook up transition element.
        detailsPresenter.setSharedElementEnterTransition(activity,
                DetailsActivity.SHARED_ELEMENT_NAME)

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH_TRAILER.toLong()) {
                val intent = Intent(activity, PlaybackOverlayActivity::class.java)
                intent.putExtra(DetailsActivity.GANK, mSelectedGank)
                startActivity(intent)
            } else {
                Toast.makeText(activity, action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        mPresenterSelector!!.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun setupMovieListRow() {
        val subcategories = arrayOf(getString(R.string.related_movies))
        val list = MovieList.list

        Collections.shuffle(list)
        val listRowAdapter = ArrayObjectAdapter(CardPresenter())
        for (j in 0..NUM_COLS - 1) {
            listRowAdapter.add(list[j % 5])
        }

        val header = HeaderItem(0, subcategories[0])
        mAdapter!!.add(ListRow(header, listRowAdapter))
    }

    private fun setupMovieListRowPresenter() {
        mPresenterSelector!!.addClassPresenter(ListRow::class.java, ListRowPresenter())
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any,
                                   rowViewHolder: RowPresenter.ViewHolder, row: Row) {

            if (item is Movie) {
                val movie = item
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(resources.getString(R.string.movie), mSelectedGank)
                intent.putExtra(resources.getString(R.string.should_start), true)
                startActivity(intent)


                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        (itemViewHolder.view as ImageCardView).mainImageView,
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle()
                activity.startActivity(intent, bundle)
            }
        }
    }

    companion object {
        private val TAG = "GankDetailsFragment"

        private val ACTION_WATCH_TRAILER = 1
        private val ACTION_RENT = 2
        private val ACTION_BUY = 3

        private val DETAIL_THUMB_WIDTH = 274
        private val DETAIL_THUMB_HEIGHT = 274

        private val NUM_COLS = 10
    }
}
