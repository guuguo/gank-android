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

package top.guuguo.ganktv;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.guuguo.gank.model.entity.GankModel;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        GankModel gank = (GankModel) item;

        if (gank != null) {
            viewHolder.getTitle().setText(gank.getDesc());
            viewHolder.getSubtitle().setText(gank.getDesc());
            viewHolder.getBody().setText(gank.getDesc());
        }
    }
}
