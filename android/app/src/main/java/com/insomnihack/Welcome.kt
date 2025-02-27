package com.insomnihack

import android.util.Log
import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.insomnihack.utils.JniThingies
import com.insomnihack.utils.LocalGameState

class Welcome(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    /* This is not the complete final flag; it will be mangled by the Rust module */
    private val UUID: String = "6AD9231A-FA61-4CE6-B2D9-259500B82BF4";

    override fun getName() = "WelcomeCTF"

    /* JNI modules (Rust) */
    external fun JNImangle (part1: String, part2: String): String
    /* ------------------ */

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun showToast(message: String): String = JNImangle (UUID, message)

    @ReactMethod
    fun matched() = LocalGameState.getInstance().matched()

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getScore(): Int {
        val score = JniThingies.getInstance().score
        val localScore = LocalGameState.getInstance().localScore

        if (score != localScore) {

            LocalGameState.getInstance().resetScore()
            Toast.makeText(reactApplicationContext, "No cheating!", Toast.LENGTH_SHORT).show()
        }

        return score
    }

    /* Shown when score == -1234 */
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getSpecialFlag(): String {

        var s = "I don't think you deserve a flag... yet"

        if (getScore() == -1234) {

            s = "Your current score is kinda sus... 🤔\nAnyway, here's a special flag -> "
            s += JniThingies.getInstance().genSpecialFlag()

            Log.i ("CTF", s)
        }

        return s
    }
}
