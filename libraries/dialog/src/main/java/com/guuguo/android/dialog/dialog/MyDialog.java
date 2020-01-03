package com.guuguo.android.dialog.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.guuguo.android.dialog.R;
import com.guuguo.android.dialog.base.BaseAlertDialog;
import com.guuguo.android.dialog.utils.CornerUtils;

/**
 * Created by mimi on 2016-11-11.
 */
@SuppressWarnings("deprecation")
public abstract class MyDialog<T extends BaseAlertDialog<T>> extends BaseAlertDialog<T> {

    /**
     * title underline
     */
    protected View mVLineTitle;
    /**
     * vertical line between btns
     */
    protected View mVLineVertical;
    /**
     * vertical line between btns
     */
    protected View mVLineVertical2;
    /**
     * horizontal line above btns
     */
    protected View mVLineHorizontal;
    /**
     * title underline color(标题下划线颜色)
     */
    protected int mTitleLineColor = Color.parseColor("#61AEDC");
    /**
     * title underline height(标题下划线高度)
     */
    protected float mTitleLineHeight = 1f;
    /**
     * btn divider line color(对话框之间的分割线颜色(水平+垂直))
     */
    protected int mDividerColor = Color.parseColor("#DCDCDC");

    /**
     * btn mButtonType(按钮类型0是内部)
     */

    static final int BTN_STYLE_FLAT = 0;
    static final int BTN_STYLE_OUTLINE = 1;
    static final int BTN_STYLE_RAISED = 2;

    private int mButtonType = BTN_STYLE_FLAT;

    private int mPrimaryBtnColor;
    private int mPrimaryBtnPressColor;
    private boolean mIsDfaultWidth = false;

    public static class DialogSetting {
        int primaryBtnColor = 0;
        int primaryBtnPressColor = 0;
        int cornerRadius = 0;
        int isTitleShow = 0;
    }

    public MyDialog(Context context) {
        super(context);

        /** default value*/
        setMTitleTextSize(17f);
        setMTitleTextColor(Color.parseColor("#000000"));
        mTitleLineColor = Color.parseColor("#33000000");
        widthRatio(0.85f);
        setMContentTextColor(Color.parseColor("#383838"));
        setMContentTextSize(17f);
        setMLeftBtnTextColor(Color.parseColor("#000000"));
        setMRightBtnTextColor(Color.parseColor("#ffffff"));
        setMMiddleBtnTextColor(Color.parseColor("#ffffff"));

        mPrimaryBtnPressColor = Color.parseColor("#e12020");
        mPrimaryBtnColor = getColor(R.color.colorPrimary); //Color.parseColor("#f23131");
        setMCornerRadius(5);
        /** default value*/
    }

    @Override
    public View onCreateView() {
        mDialogContent.setGravity(Gravity.CENTER);


        /** llcontainer **/
        if (!mIsDfaultWidth) {
            getMLlContainer().setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.dialog_width), ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        /** title */
        getMTvTitle().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        getMTvTitle().setGravity(Gravity.CENTER_HORIZONTAL);
//        mTvTitle.setForegroundGravity(Gravity.CENTER_HORIZONTAL);

        getMLlContainer().addView(getMTvTitle());

        /** title underline */
        mVLineTitle = new View(mContext);
        getMLlContainer().addView(mVLineTitle);


        getMLlContainer().addView(createCustomContent());

        mVLineHorizontal = new View(mContext);
        mVLineHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        getMLlContainer().addView(mVLineHorizontal);

        /** btns */

        getMTvBtnLeft().setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(43), 1));
        getMLlBtns().addView(getMTvBtnLeft());

        mVLineVertical = new View(mContext);
        mVLineVertical.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        getMLlBtns().addView(mVLineVertical);

        getMTvBtnMiddle().setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(43), 1));
        getMLlBtns().addView(getMTvBtnMiddle());

        mVLineVertical2 = new View(mContext);
        mVLineVertical2.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        getMLlBtns().addView(mVLineVertical2);

        getMTvBtnRight().setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(43), 1));
        getMLlBtns().addView(getMTvBtnRight());


        getMLlContainer().addView(getMLlBtns());

        return getMLlContainer();
    }

    protected abstract View createCustomContent();

    protected abstract void initCustomContent();

    @Override
    public void setUiBeforShow() {
        super.setUiBeforShow();


        /** title */
        getMTvTitle().setMinHeight(dp2px(48));
        getMTvTitle().setGravity(Gravity.CENTER_VERTICAL);
        getMTvTitle().setPadding(dp2px(15), dp2px(5), dp2px(0), dp2px(5));
        getMTvTitle().setVisibility(getMIsTitleShow() ? View.VISIBLE : View.GONE);


        /** title underline */
        mVLineTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dp2px(mTitleLineHeight)));
        mVLineTitle.setBackgroundColor(mTitleLineColor);
        mVLineTitle.setVisibility(getMIsTitleShow() ? View.VISIBLE : View.GONE);

        /** content */

        getMTvContent().setPadding(dp2px(15), dp2px(30), dp2px(15), dp2px(30));
        getMTvContent().setMinHeight(dp2px(120));
        getMTvContent().setGravity(getMContentGravity());

        initCustomContent();
        /** btns */
        /**set background color and corner radius */
        float radius = dp2px(getMCornerRadius());
        getMLlContainer().setBackgroundDrawable(CornerUtils.cornerDrawable(getMBgColor(), radius));
        getMTvBtnLeft().setBackgroundDrawable(CornerUtils.btnSelector(radius, getMBgColor(), getMBtnPressColor(), 0));
        getMTvBtnRight().setBackgroundDrawable(CornerUtils.btnSelector(radius, mPrimaryBtnColor, mPrimaryBtnPressColor, 1));
        getMTvBtnMiddle().setBackgroundDrawable(CornerUtils.btnSelector(getMBtnNum() == 1 ? radius : 0, getMBgColor(), getMBtnPressColor(), -1));
        getMTvBtnMiddle().setTextColor(Color.BLACK);

        switch (mButtonType) {
            case BTN_STYLE_FLAT: {
                mVLineHorizontal.setBackgroundColor(mDividerColor);
                mVLineVertical.setBackgroundColor(mDividerColor);
                mVLineVertical2.setBackgroundColor(mDividerColor);

                if (getMBtnNum() == 1) {
                    getMTvBtnLeft().setVisibility(View.GONE);
                    getMTvBtnRight().setVisibility(View.GONE);
                    mVLineVertical.setVisibility(View.GONE);
                    mVLineVertical2.setVisibility(View.GONE);
                } else if (getMBtnNum() == 2) {
                    getMTvBtnMiddle().setVisibility(View.GONE);
                    mVLineVertical.setVisibility(View.GONE);
                }
                break;
            }
            case BTN_STYLE_OUTLINE: {
                mVLineHorizontal.setBackgroundColor(Color.TRANSPARENT);
                mVLineVertical.setBackgroundColor(Color.TRANSPARENT);
                mVLineVertical2.setBackgroundColor(Color.TRANSPARENT);

                if (getMBtnNum() == 1) {
                    getMTvBtnLeft().setVisibility(View.GONE);
                    getMTvBtnRight().setVisibility(View.GONE);
                    mVLineVertical.setVisibility(View.GONE);
                    mVLineVertical2.setVisibility(View.GONE);
//                    ((LinearLayout.LayoutParams) mTvBtnMiddle.getLayoutParams()).setMargins(dp2px(8), dp2px(8), dp2px(8), dp2px(8));
//                    mTvBtnMiddle.setBackgroundDrawable(CornerUtils.btnSelector(dp2px(30), mBgColor, mBtnPressColor, -1));
                }
            }
        }


    }


    // --->属性设置
    public T btnType(int buttonType) {
        this.mButtonType = buttonType;
        return (T) this;
    }

    /**
     * set title underline color(设置标题下划线颜色)
     */
    public T titleLineColor(int titleLineColor) {
        this.mTitleLineColor = titleLineColor;
        return (T) this;
    }

    /**
     * set title underline height(设置标题下划线高度)
     */
    public T titleLineHeight(float titleLineHeight_DP) {
        this.mTitleLineHeight = titleLineHeight_DP;
        return (T) this;
    }

    /**
     * set divider color between btns(设置btn分割线的颜色)
     */
    public T dividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        return (T) this;
    }

    /**
     * set divider color between btns(设置btn分割线的颜色)
     */
    public T setDefaultWidth(boolean bool) {
        this.mIsDfaultWidth = bool;
        return (T) this;
    }
}
