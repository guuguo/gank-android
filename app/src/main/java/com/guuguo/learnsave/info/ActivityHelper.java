package com.guuguo.learnsave.info;

import android.app.Activity;
import android.content.Intent;

import com.guuguo.androidlib.helper.BaseActivityHelper;
import com.guuguo.learnsave.app.activity.DateGankActivity;

/**
 * Created by guodeqing on 7/24/16.
 */

public class ActivityHelper extends BaseActivityHelper {
    public static final int ACTIVITY_DATE_GANK = 0;

    public static void startDateGank(Activity activity) {
        Intent intent = new Intent(activity, DateGankActivity.class);
        activity.startActivityForResult(intent, ACTIVITY_DATE_GANK);
    }
}
