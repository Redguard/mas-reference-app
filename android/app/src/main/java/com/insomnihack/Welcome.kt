package com.insomnihack

import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class Welcome(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    /* This is not the complete final flag; it will be mangled by the Rust module */
    private val UUID: String = "6AD9231A-FA61-4CE6-B2D9-259500B82BF4";
    private var localScore: Int = 0

    override fun getName() = "WelcomeCTF"

    /* JNI modules (Rust) */
    external fun JNImangle (part1: String, part2: String): String
    /* ------------------ */

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun showToast(message: String): String = JNImangle (UUID, message)

    @ReactMethod
    fun matched() {
        localScore += 1
        JniThingies.getInstance().newMatch()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getScore(): Int {
        val score = JniThingies.getInstance().getScore ()

        if (score != localScore) {

            JniThingies.getInstance().resetScore()
            localScore = 0
            Toast.makeText(reactApplicationContext, "No cheating!", Toast.LENGTH_SHORT).show()
        }

        return score
    }

    /* Shown when score >= 1000 */
    fun getSpecialFlag(): String {

        var s = ""

        if (getScore() >= 1000) {

            s = "Wow, you've already more than 1k pairs, what a memory! Here's your special flag -> "
            s += JniThingies.getInstance().genHighScoreFlag()
        }

        return s
    }
}
