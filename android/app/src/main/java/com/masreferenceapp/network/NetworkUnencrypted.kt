package com.masreferenceapp.network

import android.net.DnsResolver
import android.net.DnsResolver.DnsException
import android.os.CancellationSignal
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Constants
import com.masreferenceapp.Status
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.Socket
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class NetworkUnencrypted(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkUnencrypted"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun resolveDns(): String {

        // Create a new instance of DnsResolver
        val resolver = DnsResolver.getInstance()
        val mExecutor: Executor = Executors.newSingleThreadExecutor()
        val mcancellationSignal = CancellationSignal()
        val callback: DnsResolver.Callback<*> = object : DnsResolver.Callback<Any?> {
            override fun onAnswer(answer: Any, rcode: Int) {
                println(answer.toString())
            }

            override fun onError(error: DnsException) {}
        }

        // Resolve the domain name to its IP addresses
        resolver.query(
            null,
            Constants.remoteWebViewHttpDomain,
            DnsResolver.TYPE_A,
            DnsResolver.FLAG_NO_CACHE_LOOKUP,
            mExecutor,
            mcancellationSignal,
            callback
        )
        return Status.status("OK", resolver.toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun standardHTTP(): String {
        return try {
            val url = URL("http://" + Constants.remoteWebViewHttpDomain)
            val urlConnection = url.openConnection() as HttpURLConnection
            val response = StringBuilder()
            urlConnection.disconnect()
            Status.status("OK", urlConnection.responseMessage)
        } catch (e: Exception) {
            Status.status("FAIL", e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nonStandardHTTP(): String {
        return try {
            val url =
                URL("http", Constants.remoteWebViewHttpDomain, Constants.remoteWebViewHttpPort, "/")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.disconnect()
            Status.status("OK", urlConnection.responseMessage)
        } catch (e: Exception) {
            Status.status("FAIL", e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawTcp(): String {
        val socket: Socket
        return try {
            socket = Socket(Constants.remoteWebViewHttpDomain, 80)
            // Get output stream to send data
            val outputStream = socket.getOutputStream()
            val request =
                "GET / HTTP/1.1\\nHost: " + Constants.remoteWebViewHttpDomain + "\\nX-A:msaTest\\n\\n"
            val output = socket.getOutputStream()
            val writer = PrintWriter(output, true)
            writer.print(request)
            socket.close()
            Status.status("OK", socket.toString())
        } catch (e: Exception) {
            Status.status("FAIL", "Failed to init socket.")
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawUdp(): String {
        return try {
            // Create a new UDP socket
            val socket = DatagramSocket()
            val serverAddress = InetAddress.getByName(Constants.remoteWebViewHttpDomain)
            val sendData = "HelloUDP".toByteArray()
            val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, 53)

            // Send the UDP packet
            socket.send(sendPacket)
            socket.close()
            Status.status("OK", socket.toString())
        } catch (e: Exception) {
            Status.status("FAIL", "Failed to init socket.")
        }
    }
}