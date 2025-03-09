package com.insomnihack

import android.util.Log
import android.util.Xml
import android.widget.Toast
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.insomnihack.network.RestClient
import com.insomnihack.utils.JniThingies
import com.insomnihack.utils.LocalGameState
import org.xmlpull.v1.XmlSerializer
import java.io.StringWriter

class Welcome(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    /* This is not the complete final flag; it will be mangled by the Rust module */
    private val UUID: String = "6AD9231A-FA61-4CE6-B2D9-259500B82BF4";

    override fun getName() = "WelcomeCTF"

    /* JNI modules (Rust) */
    external fun enableExperimentalGuiNative(): String
    /* ------------------ */

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun enableExperimentalGui()  {
        Log.i("CTF", "Enabling experimental GUI. Careful, this is an untested feature.")
        val result = enableExperimentalGuiNative()
        // i know its stupid, but it looks more plausible in the log
        try {
            if ("Error" in result){
                throw RuntimeException("Runtime Error in native code occurred: $result")
            }
        }
        catch (e: Exception){
            Log.e("CTF", e.toString())
        }
    }

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

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getStreak (): Int = LocalGameState.getInstance().streak

    @ReactMethod
    fun addStreak() = LocalGameState.getInstance().addStreak ()

    @ReactMethod
    fun resetStreak () = LocalGameState.getInstance().resetStreak()

    /* Shown when score == -1234 */
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getSpecialFlag(): String {

        var s = "I don't think you deserve a flag... yet"

        if (getScore() == -1234) {

            /* Makes it easier for players to understand they didn't really get the actual flag */
            val flag = JniThingies.getInstance().genSpecialFlag()

            if (flag != "Come back when you have a better score") {

                s = "Your current score is kinda sus... 🤔\nAnyway, here's a special flag -> "
                s += JniThingies.getInstance().genSpecialFlag()

                Log.i ("CTF", s)
            }

        }

        return s
    }

    @ReactMethod
    fun serialiseScore (score: Int, fastestTime: Int, leastMoves: Int, handleResult: Callback) {

        val serializer: XmlSerializer = Xml.newSerializer()
        val stringWriter = StringWriter()
        serializer.setOutput(stringWriter)

        // XML generation
        serializer.startDocument("UTF-8", true)
        serializer.startTag("", "game_stats")

        serializer.startTag("", "score")
        serializer.text(score.toString())
        serializer.endTag("", "score")

        serializer.startTag("", "fastest_time")
        serializer.text(fastestTime.toString())
        serializer.endTag("", "fastest_time")

        serializer.startTag("", "least_moves")
        serializer.text(leastMoves.toString())
        serializer.endTag("", "least_moves")

        serializer.startTag("", "flag")
        serializer.text(JniThingies.getInstance().genFlagFromMetadata("score_file"))
        serializer.endTag("", "flag")

        serializer.endTag("", "game_stats")
        serializer.endDocument()

        val rawXml = stringWriter.toString()

        val signature = RsaThingies().signData(rawXml.toByteArray())
        val signedXml = "$rawXml§$signature"

        handleResult.invoke (signedXml)
    }

    @ReactMethod
    fun submitFeedback (apiKey: String, name: String, feedback: String, successCallback: Callback, errorCallback: Callback) {
        RestClient(apiKey).sendFeedback (name, feedback,
            onSuccess = { response ->
                Log.i("CTF", "Feedback submitted! Response: $response")
                successCallback.invoke (response)
            },
            onFailure = { error ->
                Log.i ("CTF", "Error submitting feedback: ${error.localizedMessage}")
                errorCallback.invoke (error.toString())
            }
        )
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun scoreboardHeartbeat (subdomain: String) {
        // TODO
    }
}
