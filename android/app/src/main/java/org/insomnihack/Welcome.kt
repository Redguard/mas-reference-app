package org.insomnihack

import android.util.Log
import android.util.Xml
import android.widget.Toast
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.insomnihack.network.RestClient
import org.masreferenceapp.MasSettings
import org.xmlpull.v1.XmlSerializer
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import org.insomnihack.utils.NetworkHelpers
import org.insomnihack.utils.createTLSSocket
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

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getIpcToken(): String = org.insomnihack.ui.GameController.AUTH_TOKEN

    @ReactMethod
    fun matched() = org.insomnihack.utils.LocalGameState.getInstance().matched()

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getScore(): Int {
        val score = org.insomnihack.utils.JniThingies.getInstance().score
        val localScore = org.insomnihack.utils.LocalGameState.getInstance().localScore

        if (score != localScore) {

            org.insomnihack.utils.LocalGameState.getInstance().resetScore()
            Toast.makeText(reactApplicationContext, "No cheating!", Toast.LENGTH_SHORT).show()
        }

        return score
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getStreak (): Int = org.insomnihack.utils.LocalGameState.getInstance().streak

    @ReactMethod
    fun addStreak() = org.insomnihack.utils.LocalGameState.getInstance().addStreak ()

    @ReactMethod
    fun resetStreak () = org.insomnihack.utils.LocalGameState.getInstance().resetStreak()

    /* Shown when score == -1234 */
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getSpecialFlag(): String {

        var s = "I don't think you deserve a flag... yet"

        if (getScore() == -1234) {

            /* Makes it easier for players to understand they didn't really get the actual flag */
            val flag = org.insomnihack.utils.JniThingies.getInstance().genSpecialFlag()

            if (flag != "Come back when you have a better score") {

                s = "Your current score is kinda sus... 🤔\nAnyway, here's a special flag -> "
                s += org.insomnihack.utils.JniThingies.getInstance().genSpecialFlag()

                Log.i ("CTF", s)
            }

        }

        return s
    }

    @ReactMethod
    fun serialiseScore (time: Int, moves: Int, handleResult: Callback) {

        val serializer: XmlSerializer = Xml.newSerializer()
        val stringWriter = StringWriter()
        serializer.setOutput(stringWriter)

        // XML generation
        serializer.startDocument("UTF-8", true)
        serializer.startTag("", "game_stats")

        serializer.startTag("", "score")
        serializer.text(getScore().toString())
        serializer.endTag("", "score")

        serializer.startTag("", "streak")
        serializer.text(getStreak().toString())
        serializer.endTag("", "streak")

        serializer.startTag("", "total_time")
        serializer.text(time.toString())
        serializer.endTag("", "total_time")

        serializer.startTag("", "moves")
        serializer.text(moves.toString())
        serializer.endTag("", "moves")

        serializer.startTag("", "flag")
        serializer.text(org.insomnihack.utils.JniThingies.getInstance().genFlagFromMetadata("score_file"))
        serializer.endTag("", "flag")

        serializer.endTag("", "game_stats")
        serializer.endDocument()

        val rawXml = stringWriter.toString()

        val signature = org.insomnihack.RsaThingies().signData(rawXml.toByteArray())
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
                Log.i("CTF", command);
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
        // INIT
        sendCommand(helper.decode("rwVeaQ=="), true);
        // heartbeat
        sendCommand(helper.decode("ji52T70+XS8U"), false);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getScoreboard () {
        // https request, not pinned, no domain/path obfuscation
        val testDomain = "scoreboard." + MasSettings.getTestDomain()
        try {
            val connection = URL("https://$testDomain/board/e364000e-75e7-4f05-9b0e-0690f1a14453.html").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            // Log.i("CTF", data)
            // do something useful here
        } catch (e: Exception) {
            Log.e("CTF", e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getTeams () {
        // https request, pinned using OKHTTP and inline, domain/path is obfuscated
        val helper = NetworkHelpers()

        try {
            val host = "teams." + MasSettings.getTestDomain()
            val port = 443

            val tlsSocket = createTLSSocket(host, port)

            val outputStream: OutputStream = tlsSocket.outputStream
            val inputStream: InputStream = tlsSocket.inputStream

            val message = helper.decode("oQ5DHeYoXS8Nod+BFmdxVm5qeBJ5Knzkrrmriqn66DSevWKo8iNiNerJIELOEEVpp9ruMJiohCroRWtbovbHyHhj1U+Y0wqc8r8pJSP+As8OQs4nRLi3xoj7TbIy5xzweGFTb79x03aqeyTjhiyRt3qnOxzAGd5vECVK4XjNLv9iwTiusUbQH8t/eAgkfC/jIEvJvoqjD/7PxQ==")
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
