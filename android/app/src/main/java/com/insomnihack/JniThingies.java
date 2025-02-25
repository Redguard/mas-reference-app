package com.insomnihack;

import android.util.Log;

public class JniThingies {

    /* JNI modules */
    private static native String JNIgenHighScoreFlag ();
    private static native void JNInewMatch ();
    private static native int JNIgetScore ();
    private static native void JNIresetScore ();
    private static native void JNIadd (int amount);
    /* ----------- */

    private static JniThingies singleton = null;

    public static JniThingies getInstance () {

        if (singleton == null) {
            singleton = new JniThingies ();
        }

        return singleton;
    }

    /* ----------- */

    protected String genHighScoreFlag () {
        return JNIgenHighScoreFlag ();
    }

    protected void newMatch () {
        JNInewMatch ();
    }

    public int getScore () {
        return JNIgetScore ();
    }

    protected void resetScore () {
        JNIresetScore ();
    }

    public void add (int amount) {
        JNIadd (amount);
    }
}
