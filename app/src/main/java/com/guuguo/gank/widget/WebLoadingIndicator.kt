/*
 * Copyright (C)  Justson(https://github.com/Justson/AgentWeb)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guuguo.gank.widget;

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.guuguo.android.lib.extension.inflater
import com.guuguo.gank.R
import com.just.agentweb.BaseIndicatorSpec
import com.just.agentweb.BaseIndicatorView

/**
 * @author cenxiaozhong
 * @since 1.0.0
 */
class WebLoadingIndicator @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseIndicatorView(context, attrs, defStyleAttr), BaseIndicatorSpec {
    var view: ImageView
    val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)

    init {
        context.inflater.inflate(R.layout.part_web_load_layout, this)
        view = findViewById(R.id.iv_circle)


        setBackgroundColor(Color.WHITE)
    }


    override fun show() {
        this.isVisible = true
        view.startAnimation(rotateAnimation)
    }


    override fun hide() {
        this.isVisible = false
        view.clearAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun offerLayoutParams(): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    companion object {

        /**
         * 默认的高度
         */
        var WEB_INDICATOR_DEFAULT_HEIGHT = 3
    }
}
