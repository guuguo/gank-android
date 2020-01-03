package com.guuguo.android.dialog.dialog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by guodeqing on 6/23/16.
 */
@SuppressWarnings("depreion")
public class CustomAlertDialog extends MyDialog<CustomAlertDialog> {


    private FrameLayout mContentLayout;
    protected View mContentView;

    public CustomAlertDialog(Context context) {
        super(context);
    }


    @Override
    protected View createCustomContent() {
        /** content */
        mContentLayout = new FrameLayout(mContext);
        mContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return mContentLayout;
    }

    @Override
    protected void initCustomContent() {
        /** content */
        if (mContentView != null && mContentLayout.indexOfChild(mContentView) == -1) {
            mContentLayout.addView(mContentView);
        }
        getMTvContent().setMinHeight(dp2px(120));

    }


    // --->属性设置
    public CustomAlertDialog contentView(View content) {
        this.mContentView = content;
        return this;
    }
}


