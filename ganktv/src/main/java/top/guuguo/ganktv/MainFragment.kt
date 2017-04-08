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

import android.app.Fragment
import java.net.URI

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.BrowseFragment
import android.support.v17.leanback.widget.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewFragment
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.guuguo.ganktv.app.MEIZI_COUNT
import com.guuguo.learnsave.model.Ganks
import com.guuguo.learnsave.model.entity.GankModel
import com.guuguo.learnsave.model.retrofit.ApiServer
import io.reactivex.functions.Consumer
import java.util.*

class MainFragment : BrowseFragment() {

    companion object {
        private val TAG = "MainFragment"

        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        //    private static final int NUM_ROWS = 10;
        private val NUM_COLS = 15
        private val HEADER_ID_1: Long = 1
        private val HEADER_NAME_1 = "每日"
        private val HEADER_ID_2: Long = 2
        private val HEADER_NAME_2 = "Android"
        private val HEADER_ID_3: Long = 3
        private val HEADER_NAME_3 = "IOS"
        private val HEADER_ID_4: Long = 4
        private val HEADER_NAME_4 = "Web"
    }

    private var mRowsAdapter: ArrayObjectAdapter? = null

    private var mBackgroundManager: BackgroundManager? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)

        prepareBackgroundManager()
        setupUIElements()
//        loadRows()
        createRows()
        setupEventListeners()

    }


    private fun createRows() {
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val headerItem1 = HeaderItem(HEADER_ID_1, HEADER_NAME_1)
        val pageRow1 = PageRow(headerItem1)
        mRowsAdapter?.add(pageRow1)

        val headerItem2 = HeaderItem(HEADER_ID_2, HEADER_NAME_2)
        val pageRow2 = PageRow(headerItem2)
        mRowsAdapter?.add(pageRow2)

        val headerItem3 = HeaderItem(HEADER_ID_3, HEADER_NAME_3)
        val pageRow3 = PageRow(headerItem3)
        mRowsAdapter?.add(pageRow3)

        val headerItem4 = HeaderItem(HEADER_ID_4, HEADER_NAME_4)
        val pageRow4 = PageRow(headerItem4)
        mRowsAdapter?.add(pageRow4)
        adapter = mRowsAdapter
    }

    private class PageRowFragmentFactory internal constructor(private val mBackgroundManager: BackgroundManager) : BrowseFragment.FragmentFactory<Fragment>() {
        val map = HashMap<Long, Fragment>()
        override fun createFragment(rowObj: Any): Fragment {
            val row = rowObj as Row
            mBackgroundManager.drawable = null
            var fragment: Fragment? = map.get(row.headerItem.id);
            if (fragment == null) {
                if (row.headerItem.id == HEADER_ID_1) {
                    fragment = GankMeiziGridFragment()
                } else if (row.headerItem.id == HEADER_ID_2) {
                    fragment = GankMeiziGridFragment()
                } else if (row.headerItem.id == HEADER_ID_3) {
                    fragment = GankMeiziGridFragment()
                } else if (row.headerItem.id == HEADER_ID_4) {
                    fragment = GankMeiziGridFragment()
                }
                if (fragment == null) {
                    throw IllegalArgumentException(String.format("Invalid row %s", rowObj))
                }
                map.put(row.headerItem.id,fragment)
            }
            return fragment
        }
    }

    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager!!.attach(activity.window)
        mainFragmentRegistry.registerFragment(PageRow::class.java,
                PageRowFragmentFactory(mBackgroundManager!!))
    }

    private fun setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
//        title = getString(R.string.browse_title) // Badge, when set, takes precedent
        // over title
        headersState = BrowseFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = resources.getColor(R.color.colorPrimary)
        // set search icon color
        searchAffordanceColor = resources.getColor(R.color.search_opaque)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
                    .show()
        }
        
    }

   
   

    private inner class GridItemPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundColor(resources.getColor(R.color.default_background))
            view.setTextColor(Color.WHITE)
            view.gravity = Gravity.CENTER
            return Presenter.ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
            (viewHolder.view as TextView).text = item as String
        }

        override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}
    }


}
