package com.rainmonth.common.utils;

import android.app.Activity;
import android.content.Intent;

/**
 *
 */
public class UtilsTransActivity4MainProcess extends UtilsTransActivity {

    public static void start(final TransActivityDelegate delegate) {
        start(null, null, delegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(null, consumer, delegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final TransActivityDelegate delegate) {
        start(activity, null, delegate, UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(activity, consumer, delegate, UtilsTransActivity4MainProcess.class);
    }
}
