package com.masreferenceapp.network

import android.webkit.WebView
import android.webkit.WebViewClient
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.network.helpers.WebViewJavaScriptBridge
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.Socket
import java.net.URL


class NetworkUnencrypted(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkUnencrypted"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun openHTTP(): String {
        val testDomain = MasSettings.getTestDomain()
        val r = ReturnStatus()

        try {
            val url = URL("http://$testDomain")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 500
            connection.readTimeout = 500
            connection.connect()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
            connection.disconnect()
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nonStandardHTTP(): String {
        val testDomain = MasSettings.getTestDomain()
        val port = 3001

        val r = ReturnStatus()

        try {
            val url = URL("http", testDomain, port, "/")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 500
            connection.readTimeout = 500

            connection.connect()
            r.success("Pin verification successful. Connection to http://$testDomain:$port established. Response code was: ${connection.responseCode}.")
            connection.disconnect()
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawTcp(): String {
        val testDomain = MasSettings.getTestDomain()
        val port = 5001
        val r = ReturnStatus()

        val socket: Socket
        try {
            socket = Socket(testDomain, port)
            socket.soTimeout = 500

            val request = "This is the MAS app using TCP."
            val output = socket.getOutputStream()
            val writer = PrintWriter(output)
            writer.print(request)
            writer.flush()

            //receive the message which the server sends back
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val message = input.readLine();

            r.success("Raw TCP socket to $testDomain:$port successfully established. Received data: $message")

            socket.close()


        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawUdp(): String {
        val testDomain = MasSettings.getTestDomain()
        val port = 4001
        val r = ReturnStatus()

         try {

             val socket = DatagramSocket()
             socket.soTimeout = 500
             val serverAddress = InetAddress.getByName(testDomain)
             val sendData = "This is the MAS app using UDP.".toByteArray()
             val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, port)

             // Send the UDP packet
             socket.send(sendPacket)


             val buffer = ByteArray(512)
             val packet = DatagramPacket(buffer, buffer.size)
             socket.receive(packet);
             val receivedData = String(packet.data, 0, packet.length)

             socket.close()

             r.success("Raw UDP socket to $testDomain:$port successfully established. Received data: $receivedData")
         } catch (e: Exception) {
             r.fail(e.toString())
         }
        return r.toJsonString()
    }


    @ReactMethod()
    fun webSocket(promise: Promise) {
        val r = ReturnStatus()
        val testDomain = "ws://"+MasSettings.getTestDomain()+":2001"

        val wv = WebView(context)
        wv.settings.javaScriptEnabled = true
        wv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.evaluateJavascript("initWebSocket('$testDomain')") {
                    r.success("WebView successfully loaded.")
                }
            }
        }

        val jsBridge = WebViewJavaScriptBridge(testDomain, promise, r)
        wv.addJavascriptInterface(jsBridge, "javaScriptBridge")
        wv.loadUrl("file:///android_asset/ws.html")
    }

    //@method

}