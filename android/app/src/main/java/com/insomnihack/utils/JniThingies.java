package com.insomnihack.utils;

import android.util.Log;

public class JniThingies {

    /* JNI modules */
    private static native void JNIgameInit();
    private static native void JNIgameDestroy();

    private static native void JNIincreaseScore(int amount);
    private static native int JNIresetScore();
    private static native int JNIgetScore();

    private static native void JNIsetMetadata(String key, String value);
    private static native String JNIgetMetadata(String key);

    private static native String JNIgenFlag(String key);

    private static native int JNIgetRandomNumber();
    /* ----------- */

    private static JniThingies singleton = null;

    public static JniThingies getInstance () {

        if (singleton == null) {
            singleton = new JniThingies ();
        }

        return singleton;
    }

    private JniThingies() {
        JNIgameInit();
    }

    /* ----------- */

    protected void newMatch () {
        JNIincreaseScore(1);
    }

    public int getScore () {
        return JNIgetScore ();
    }

    protected int resetScore () {
        return JNIresetScore();
    }

    protected void add (int amount) {

        /*
         * Hacky way to figure out the caller (to show the correct flag later). The expected value
         * when clicking the button from the exported activity is:
                dalvik.system.VMStack.getThreadStackTrace(Native Method)
                java.lang.Thread.getStackTrace(Thread.java:1841)
                com.insomnihack.utils.JniThingies.add(JniThingies.java:57)
                com.insomnihack.utils.LocalGameState.add(LocalGameState.java:41)
                com.insomnihack.ui.ScoreDebuggerActivity.increaseScore(ScoreDebuggerActivity.java:48)
                com.insomnihack.ui.ScoreDebuggerActivity.-$$Nest$mincreaseScore(Unknown Source:0)
                com.insomnihack.ui.ScoreDebuggerActivity$1.onClick(ScoreDebuggerActivity.java:33)
                android.view.View.performClick(View.java:7542)
                android.view.View.performClickInternal(View.java:7519)
                android.view.View.-$$Nest$mperformClickInternal(Unknown Source:0)
                android.view.View$PerformClick.run(View.java:29476)
                android.os.Handler.handleCallback(Handler.java:942)
                android.os.Handler.dispatchMessage(Handler.java:99)
                android.os.Looper.loopOnce(Looper.java:201)
                android.os.Looper.loop(Looper.java:288)
                android.app.ActivityThread.main(ActivityThread.java:7918)
                java.lang.reflect.Method.invoke(Native Method)
                com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
                com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936)
         */
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StringBuilder stackTrace = new StringBuilder();

        for (StackTraceElement s : stack) {

            stackTrace.append (s.toString ())
                    .append("\n");
        }

        JNIincreaseScore (amount);
        JNIsetMetadata ("HighScoreCaller", new String (stackTrace));
    }

    /* ----------- */

    public String genHighScoreFlag () {

        return JNIgenFlag("HighScoreCaller");
    }
}
