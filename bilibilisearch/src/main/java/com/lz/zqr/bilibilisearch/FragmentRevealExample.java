package com.lz.zqr.bilibilisearch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.lz.zqr.bilibilisearch.utils.PdUtil;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class FragmentRevealExample extends Fragment implements View.OnClickListener{

    private View content;
//    private SupportAnimator mRevealAnimator;
    private EditText edit_search;
    private int centerX;
    private int centerY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        rootView.setOnClickListener(this);
        content = rootView.findViewById(R.id.content);
        edit_search = (EditText) rootView.findViewById(R.id.edit_search);
        final ImageView img_search = (ImageView) rootView.findViewById(R.id.img_search);
        final View edit_lay = rootView.findViewById(R.id.edit_lay);
        final View items = rootView.findViewById(R.id.items);



        edit_lay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                edit_lay.getViewTreeObserver().removeOnPreDrawListener(this);
                items.setVisibility(View.INVISIBLE);
                items.setOnClickListener(FragmentRevealExample.this);
                edit_lay.setVisibility(View.INVISIBLE);

                centerX = img_search.getLeft()+img_search.getWidth()/2;
                centerY = img_search.getTop()+img_search.getHeight()/2;

                SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(edit_lay, centerX, centerY, 20, PdUtil.hypo(edit_lay.getWidth(), edit_lay.getHeight()));
                mRevealAnimator.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                        edit_lay.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                items.setVisibility(View.VISIBLE);
                                edit_search.requestFocus();
                                if (getActivity()!=null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(edit_search, InputMethodManager.SHOW_IMPLICIT);
                                }
                            }
                        }, 100);
                    }
                    @Override
                    public void onAnimationCancel() {
                    }
                    @Override
                    public void onAnimationRepeat() {
                    }
                });
                mRevealAnimator.setDuration(200);
                mRevealAnimator.setStartDelay(100);
                mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mRevealAnimator.start();
                return true;
            }
        });

        return rootView;
    }

    public boolean onBackPressed() {
        SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(content, centerX, centerY, 20, PdUtil.hypo(content.getWidth(), content.getHeight()));
        mRevealAnimator = mRevealAnimator.reverse();
        if (mRevealAnimator==null) return false;
        mRevealAnimator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
                content.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd() {
                content.setVisibility(View.INVISIBLE);
                if (getActivity()!=null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
            @Override
            public void onAnimationCancel() {
            }
            @Override
            public void onAnimationRepeat() {
            }
        });
        mRevealAnimator.setDuration(200);
        mRevealAnimator.setStartDelay(100);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.root:
                onBackPressed();
                break;
        }
    }
}
