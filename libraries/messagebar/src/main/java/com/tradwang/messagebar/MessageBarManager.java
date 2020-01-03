package com.tradwang.messagebar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Manages {@link MessageBar}s.
 */
class MessageBarManager {

    private static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static final int LONG_DURATION_MS = 2750;

    private static MessageBarManager sMessageBarManager;

    static MessageBarManager getInstance() {
        if (sMessageBarManager == null) {
            sMessageBarManager = new MessageBarManager();
        }
        return sMessageBarManager;
    }

    private final Object mLock;
    private final Handler mHandler;

    private MessageBarRecord mCurrentMessageBar;
    private MessageBarRecord mNextMessageBar;

    private MessageBarManager() {
        mLock = new Object();
        mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case MSG_TIMEOUT:
                        handleTimeout((MessageBarRecord) message.obj);
                        return true;
                }
                return false;
            }
        });
    }

    interface Callback {

        void show();

        void dismiss(int event);
    }

    void show(int duration, Callback callback) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                // Means that the callback is already in the queue. We'll just update the duration
                mCurrentMessageBar.duration = duration;
                // If this is the MessageBar currently being shown, call re-schedule it's
                // timeout
                mHandler.removeCallbacksAndMessages(mCurrentMessageBar);
                scheduleTimeoutLocked(mCurrentMessageBar);
                return;
            } else if (isNextMessageBar(callback)) {
                // We'll just update the duration
                mNextMessageBar.duration = duration;
            } else {
                // Else, we need to create a new record and queue it
                mNextMessageBar = new MessageBarRecord(duration, callback);
            }

            if (!(mCurrentMessageBar != null && cancelMessageBarLocked(mCurrentMessageBar, MessageBar.Callback.DISMISS_EVENT_CONSECUTIVE))) {
                // Clear out the current snackbar
                mCurrentMessageBar = null;
                // Otherwise, just show it now
                showNextMessageBarLocked();
            }
        }
    }

    void dismiss(Callback callback, int event) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                cancelMessageBarLocked(mCurrentMessageBar, event);
            } else if (isNextMessageBar(callback)) {
                cancelMessageBarLocked(mNextMessageBar, event);
            }
        }
    }

    /**
     * Should be called when a MessageBar is no longer displayed. This is after any exit
     * animation has finished.
     */
    void onDismissed(Callback callback) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                // If the callback is from a MessageBar currently show, remove it and show a new one
                mCurrentMessageBar = null;
                if (mNextMessageBar != null) {
                    showNextMessageBarLocked();
                }
            }
        }
    }

    /**
     * Should be called when a MessageBar is being shown. This is after any entrance animation has
     * finished.
     */
    void onShown(Callback callback) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                scheduleTimeoutLocked(mCurrentMessageBar);
            }
        }
    }

    void cancelTimeout(Callback callback) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                mHandler.removeCallbacksAndMessages(mCurrentMessageBar);
            }
        }
    }

    void restoreTimeout(Callback callback) {
        synchronized (mLock) {
            if (isCurrentMessageBar(callback)) {
                scheduleTimeoutLocked(mCurrentMessageBar);
            }
        }
    }

    boolean isCurrent(Callback callback) {
        synchronized (mLock) {
            return isCurrentMessageBar(callback);
        }
    }

    boolean isCurrentOrNext(Callback callback) {
        synchronized (mLock) {
            return isCurrentMessageBar(callback) || isNextMessageBar(callback);
        }
    }

    private static class MessageBarRecord {

        private final WeakReference<Callback> callback;
        private int duration;

        MessageBarRecord(int duration, Callback callback) {
            this.callback = new WeakReference<>(callback);
            this.duration = duration;
        }

        boolean isSnackbar(Callback callback) {
            return callback != null && this.callback.get() == callback;
        }
    }

    private void showNextMessageBarLocked() {
        if (mNextMessageBar != null) {
            mCurrentMessageBar = mNextMessageBar;
            mNextMessageBar = null;

            final Callback callback = mCurrentMessageBar.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                // The callback doesn't exist any more, clear out the MessageBar
                mCurrentMessageBar = null;
            }
        }
    }

    private boolean cancelMessageBarLocked(MessageBarRecord record, int event) {
        final Callback callback = record.callback.get();
        if (callback != null) {
            callback.dismiss(event);
            return true;
        }
        return false;
    }

    private boolean isCurrentMessageBar(Callback callback) {
        return mCurrentMessageBar != null && mCurrentMessageBar.isSnackbar(callback);
    }

    private boolean isNextMessageBar(Callback callback) {
        return mNextMessageBar != null && mNextMessageBar.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(MessageBarRecord barRecord) {
        if (barRecord.duration == MessageBar.LENGTH_INDEFINITE) {
            // If we're set to indefinite, we don't want to set a timeout
            return;
        }

        int durationMs = LONG_DURATION_MS;
        if (barRecord.duration > 0) {
            durationMs = barRecord.duration;
        } else if (barRecord.duration == MessageBar.LENGTH_SHORT) {
            durationMs = SHORT_DURATION_MS;
        }
        mHandler.removeCallbacksAndMessages(barRecord);
        mHandler.sendMessageDelayed(Message.obtain(mHandler, MSG_TIMEOUT, barRecord), durationMs);
    }

    private void handleTimeout(MessageBarRecord record) {
        synchronized (mLock) {
            if (mCurrentMessageBar == record || mNextMessageBar == record) {
                cancelMessageBarLocked(record, MessageBar.Callback.DISMISS_EVENT_TIMEOUT);
            }
        }
    }

}