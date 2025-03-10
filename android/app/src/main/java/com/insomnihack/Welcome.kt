package com.insomnihack

import android.content.Intent
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
import com.masreferenceapp.MasSettings
import org.xmlpull.v1.XmlSerializer
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import com.insomnihack.utils.NetworkHelpers
import com.insomnihack.utils.createTLSSocket
import com.masreferenceapp.ReturnStatus
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket

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

    private fun sendCommand(command:String, print:Boolean){
        val testDomain = MasSettings.getTestDomain()
        val port = 7001
        try {

            val socket: Socket = Socket(testDomain, port)
            socket.soTimeout = 500
            val output = socket.getOutputStream()
            val writer = PrintWriter(output)
            writer.print(command)
            writer.flush()
            //receive the message which the server sends back
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val message = input.readLine()

            if(print){
                Log.i("CTF", "Answer: $message")
            }

            socket.close()
        } catch (e: Exception) {
            Log.e("CTF", e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun scoreboardHeartbeat () {
        val helper = NetworkHelpers()

        // make a raw TCP connection and ask for a heartbeat
        sendCommand(helper.decode("iy5kTqg7XQEGhpjXYj5p"), false);
        sendCommand(helper.decode("ji52T70+XS8U"), true)
        sendCommand(helper.decode("030nCapuDn9N5MWDQ3IkVGpoMQd6cH+qsLyp3/vkvWnFv3/8"), false)
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getTeams () {
        // https request, not pinned, no domain/path obfuscation
        val testDomain = "400e27f9-f9ff-4a86-8353-9c6df71a75b1." + MasSettings.getTestDomain()
        try {
            val connection = URL("https://$testDomain/e364000e-75e7-4f05-9b0e-0690f1a14453.html").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            // Log.i("CTF", data)
            // do something useful here
        } catch (e: Exception) {
            Log.e("CTF", e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun postScore () {
        // https request, pinned using OKHTTP and inline, domain/path is obfuscated
        val helper = NetworkHelpers()

        try {
            // we simply do this manually, that it is not too easy guessing the domain and path,
            // the easier way should be to bypass the custom pinning

            val host = helper.decode("0Hh1D/A/DnpN4sOBRXIkUmptMV0ocS+q57T73/Dg6jrN4Cv+")+"." + MasSettings.getTestDomain()
            val port = 443

            val tlsSocket = createTLSSocket(host, port)

            val outputStream: OutputStream = tlsSocket.outputStream
            val inputStream: InputStream = tlsSocket.inputStream

            val message = helper.decode("oQ5DHeZvCHYB4pLRQnInBGpqMQstf3uq4rWr2eXgsWmZ4Xz58iQwbO6BcFSVHVIBu9rqT4a3mxbvB0xHpbjd3j9khhuIy1PQsaFoI2ivRMwEDMlsHP3qjN66SIcP0VmlMyhzJrd+1GH4UwPV1Buasnr4PwLTUpFcB1hg91/ufJVCyzjNzz+fI8B+fAksYSX5SUbWvpSoa5yjrFyKDTy33Mmyepu6zfz6nCuDa75DpRHBW8wGOVsl")
            outputStream.write(message.toByteArray(Charsets.UTF_8))
            outputStream.flush()

            val buffer = ByteArray(4096)
            val bytesRead = inputStream.read(buffer)
            // println("Server response: ${String(buffer, 0, bytesRead)}")
            // do something useful here

            tlsSocket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
