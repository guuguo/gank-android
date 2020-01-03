package com.tradwang.messagebar;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.SwipeDismissBehavior;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public final class MessageBar {

    public static abstract class Callback {

        public static final int DISMISS_EVENT_SWIPE = 0;

        public static final int DISMISS_EVENT_ACTION = 1;

        public static final int DISMISS_EVENT_TIMEOUT = 2;

        public static final int DISMISS_EVENT_MANUAL = 3;

        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        @IntDef({DISMISS_EVENT_SWIPE, DISMISS_EVENT_ACTION, DISMISS_EVENT_TIMEOUT, DISMISS_EVENT_MANUAL, DISMISS_EVENT_CONSECUTIVE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public void onDismissed(MessageBar messageBar, @DismissEvent int event) {

        }

        public void onShown(MessageBar messageBar) {

        }
    }

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int LENGTH_INDEFINITE = -2;

    public static final int LENGTH_SHORT = -1;

    public static final int LENGTH_LONG = 0;

    private static final int ANIMATION_DURATION = 300;

    private static final Handler sHandler;

    private static final int MSG_SHOW = 0;

    private static final int MSG_DISMISS = 1;

    static {
        sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case MSG_SHOW:
                        ((MessageBar) message.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((MessageBar) message.obj).hideView(message.arg1);
                        return true;
                }
                return false;
            }
        });
    }

    private final ViewGroup mParent;
    private final MessageBarLayout mView;
    private int mDuration;
    private Callback mCallback;

    private MessageBar(View dep, int contentView) {
        mParent = findSuitableParent(dep);
        mView = new MessageBarLayout(mParent.getContext());
        mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(mView.getContext()).inflate(contentView, mView, true);
    }

    @NonNull
    public static MessageBar make(@NonNull View view, @LayoutRes int contentView, @Duration int duration) {
        MessageBar messageBar = new MessageBar(view, contentView);
        messageBar.setDuration(duration);
        return messageBar;
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return fallback;
    }

    @NonNull
    public MessageBar setDuration(@Duration int duration) {
        mDuration = duration;
        return this;
    }

    @Duration
    public int getDuration() {
        return mDuration;
    }

    @NonNull
    public View getContentView() {
        return mView.getChildAt(0);
    }

    public void show() {
        MessageBarManager.getInstance().show(mDuration, mManagerCallback);
    }

    public void dismiss() {
        dispatchDismiss(Callback.DISMISS_EVENT_MANUAL);
    }

    private void dispatchDismiss(@Callback.DismissEvent int event) {
        MessageBarManager.getInstance().dismiss(mManagerCallback, event);
    }

    @NonNull
    public MessageBar setCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    public boolean isShown() {
        return MessageBarManager.getInstance().isCurrent(mManagerCallback);
    }

    public boolean isShownOrQueued() {
        return MessageBarManager.getInstance().isCurrentOrNext(mManagerCallback);
    }

    private final MessageBarManager.Callback mManagerCallback = new MessageBarManager.Callback() {
        @Override
        public void show() {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_SHOW, MessageBar.this));
        }

        @Override
        public void dismiss(int event) {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, event, 0, MessageBar.this));
        }
    };

    private void showView() {
        if (mView.getParent() == null) {
            final ViewGroup.LayoutParams lp = mView.getLayoutParams();

            if (lp instanceof CoordinatorLayout.LayoutParams) {

                final Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
                behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {
                        dispatchDismiss(Callback.DISMISS_EVENT_SWIPE);
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case SwipeDismissBehavior.STATE_DRAGGING:
                            case SwipeDismissBehavior.STATE_SETTLING:
                                MessageBarManager.getInstance().cancelTimeout(mManagerCallback);
                                break;
                            case SwipeDismissBehavior.STATE_IDLE:
                                MessageBarManager.getInstance().restoreTimeout(mManagerCallback);
                                break;
                        }
                    }
                });
                ((CoordinatorLayout.LayoutParams) lp).setBehavior(behavior);
            }
            mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mParent.addView(mView);
        }

        mView.setOnAttachStateChangeListener(new MessageBarLayout.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShownOrQueued()) {
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onViewHidden(Callback.DISMISS_EVENT_MANUAL);
                        }
                    });
                }
            }
        });

        if (ViewCompat.isLaidOut(mView)) {
            animateViewIn();
        } else {
            mView.setOnLayoutChangeListener(new MessageBarLayout.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    animateViewIn();
                    mView.setOnLayoutChangeListener(null);
                }
            });

            mView.setOnTouchEventListener(new MessageBarLayout.OnSwipeOutListener() {
                @Override
                public void onSwipeOut() {
                    dismiss();
                }
            });
        }
    }

    private void animateViewIn() {
        mView.setTranslationY(-mView.getHeight());
        ViewCompat.animate(mView)
                .translationY(0f)
                .setInterpolator(MessageBarAnim.FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setDuration(ANIMATION_DURATION)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        if (mCallback != null) {
                            mCallback.onShown(MessageBar.this);
                        }
                        MessageBarManager.getInstance().onShown(mManagerCallback);
                    }
                }).start();
    }

    private void animateViewOut(final int event) {
        ViewCompat.animate(mView)
                .translationY(-mView.getHeight())
                .setInterpolator(MessageBarAnim.FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setDuration(ANIMATION_DURATION)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        onViewHidden(event);
                    }
                })
                .start();
    }

    private void hideView(int event) {
        if (mView.getVisibility() != View.VISIBLE || isBeingDragged()) {
            onViewHidden(event);
        } else {
            animateViewOut(event);
        }
    }

    private void onViewHidden(int event) {
        MessageBarManager.getInstance().onDismissed(mManagerCallback);
        if (mCallback != null) {
            mCallback.onDismissed(this, event);
        }
        final ViewParent parent = mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mView);
        }
    }


    private boolean isBeingDragged() {
        final ViewGroup.LayoutParams lp = mView.getLayoutParams();

        if (lp instanceof CoordinatorLayout.LayoutParams) {
            final CoordinatorLayout.LayoutParams cllp = (CoordinatorLayout.LayoutParams) lp;
            final CoordinatorLayout.Behavior behavior = cllp.getBehavior();

            if (behavior instanceof SwipeDismissBehavior) {
                return ((SwipeDismissBehavior) behavior).getDragState() != SwipeDismissBehavior.STATE_IDLE;
            }
        }
        return false;
    }

    static class MessageBarLayout extends FrameLayout {

        private float downY = 0f;
        private float totalDownY = 0f;

        private OnLayoutChangeListener mOnLayoutChangeListener;
        private OnAttachStateChangeListener mOnAttachStateChangeListener;
        private OnSwipeOutListener mOnSwipeOutListener;

        interface OnLayoutChangeListener {
            void onLayoutChange(View view, int left, int top, int right, int bottom);
        }

        interface OnAttachStateChangeListener {

            void onViewAttachedToWindow(View v);

            void onViewDetachedFromWindow(View v);
        }

        interface OnSwipeOutListener {

            void onSwipeOut();
        }

        MessageBarLayout(Context context) {
            this(context, null);
        }

        MessageBarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            setClickable(true);
            ViewCompat.setAccessibilityLiveRegion(this, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (mOnLayoutChangeListener != null) {
                mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (mOnAttachStateChangeListener != null) {
                mOnAttachStateChangeListener.onViewAttachedToWindow(this);
            }
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (mOnAttachStateChangeListener != null) {
                mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            mOnLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
            mOnAttachStateChangeListener = listener;
        }

        void setOnTouchEventListener(OnSwipeOutListener onTouchEventListener) {
            this.mOnSwipeOutListener = onTouchEventListener;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    totalDownY = event.getRawY() - downY;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (totalDownY < -50f) {
                        if (mOnSwipeOutListener != null) {
                            mOnSwipeOutListener.onSwipeOut();
                        }
                        return true;
                    }
                    break;
            }
            return super.onInterceptTouchEvent(event);
        }
    }

    final class Behavior extends SwipeDismissBehavior<MessageBarLayout> {

        @Override
        public boolean canSwipeDismissView(@NonNull View child) {
            return child instanceof MessageBarLayout;
        }

        @Override
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, @NonNull MessageBarLayout child, MotionEvent event) {

            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        MessageBarManager.getInstance().cancelTimeout(mManagerCallback);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        MessageBarManager.getInstance().restoreTimeout(mManagerCallback);
                        break;
                }
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}