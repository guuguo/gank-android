package com.guuguo.android.dialog.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.guuguo.android.dialog.R;

/**
 * Created by guodeqing on 6/23/16.
 */
@SuppressWarnings("deprecation")
public class EditAlertDialog extends MyDialog<EditAlertDialog> {

    private FrameLayout mContentLayout;


    private EditText mEditTextView;
    private String mEditText;
    private int mInputType = -1;
    private String mHint = "";


    public EditAlertDialog(Context context) {
        super(context);
    }


    public EditText getEditTextView() {
        return mEditTextView;
    }

    @Override
    protected View createCustomContent() {
        mContentLayout = new FrameLayout(mContext);
        mContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        /** EditText */
        mEditTextView = new EditText(mContext);
        mEditTextView.setBackground(getDrawable(mContext, R.drawable.bg_edittext));
        mEditTextView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(45)));
        mContentLayout.addView(mEditTextView);

        return mContentLayout;
    }
    public static Drawable getDrawable( Context context, int id) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
    @Override
    protected void initCustomContent() {
        /** content */

        mContentLayout.setPadding(dp2px(30), dp2px(20), dp2px(30), dp2px(20));
        mContentLayout.setMinimumHeight(dp2px(120));

        /**edit text */
        if (!TextUtils.isEmpty(mEditText)) {
            mEditTextView.setText(mEditText);
        }
        if (!mHint.isEmpty()) {
            mEditTextView.setHint(mHint);
        }
        if (mInputType != -1) {
            mEditTextView.setInputType(mInputType);
        }
    }


    public EditAlertDialog setInputType(int inputType) {
        this.mInputType = inputType;
        return this;
    }

    public EditAlertDialog setHint(String hint) {
        this.mHint = hint;
        return this;
    }

    public void setEditText(String editText) {
        this.mEditText = editText;
    }

    public void setError(String error) {
        if (mEditTextView != null) {
            mEditTextView.setError(error);
        }
    }

    public String getEditText() {
        if (mEditTextView != null) {
            return mEditTextView.getText().toString();
        }
        return null;
    }
}


