/*
Copyright 2015 shizhefei（LuckyJayce）
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.guuguo.android.lib.widget.simpleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于切换布局,用一个新的布局替换掉原先的布局
 *
 * @author LuckyJayce
 */
public class VaryViewHelper {
    private View view;
    public ViewGroup parentView;
    private int viewIndex;
    private ViewGroup.LayoutParams params;
    private View currentView;

    public VaryViewHelper(View view) {
        super();
        this.view = view;
        init();
    }

    private void init() {
        params = view.getLayoutParams();
        if (view.getParent() != null) {
            parentView = (ViewGroup) view.getParent();
        } else {
            parentView = view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for (int index = 0; index < count; index++) {
            if (view == parentView.getChildAt(index)) {
                viewIndex = index;
                break;
            }
        }
        currentView = view;
    }

    public View getCurrentLayout() {
        return currentView;
    }

    public void restoreView() {
        showLayout(view);
    }

    public void showLayout(View targetView) {
        // 如果已经是那个view，那就不需要再进行替换操作了
        if (parentView.getChildAt(viewIndex) != targetView) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(targetView);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(targetView, viewIndex, params);
        }
        this.currentView = targetView;
    }

    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
    }

    public Context getContext() {
        return view.getContext();
    }

    public View getView() {
        return view;
    }
}
