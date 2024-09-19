package com.masreferenceapp.network

import android.net.DnsResolver
import android.net.DnsResolver.DnsException
import android.os.CancellationSignal
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Constants
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.Status
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.Socket
import java.net.URL
import java.net.URLConnection
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
        val r = ReturnStatus()
        try {
            val inetAddress = InetAddress.getByName(Constants.remoteHttpDomain)
            r.addStatus("OK", "Address for the domain '" + Constants.remoteHttpDomain + "' is: " + inetAddress.hostAddress)
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun standardHTTP(): String {
        val r = ReturnStatus()

        return try {
            val url = URL("http://" + Constants.remoteHttpDomain)
            val urlConnection = url.openConnection() as HttpURLConnection
            val response = StringBuilder()
            urlConnection.disconnect()
            val r = ReturnStatus("OK", "Connection established. Status code was: " + urlConnection.responseCode)
            r.toJsonString()
        } catch (e: Exception) {
            val r = ReturnStatus("FAIL", e.toString())
            return r.toJsonString()
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nonStandardHTTP(): String {
        return try {
            val url = URL("http", Constants.remoteHttpDomain, Constants.remoteHttpPort, "/")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 500 // Set a timeout for establishing the connection
            urlConnection.readTimeout = 5000 // Set a timeout for reading data from the server

            urlConnection.disconnect()
            val r = ReturnStatus("OK", "Connection established. Status code was: " + urlConnection.responseCode)
            r.toJsonString()
        } catch (e: Exception) {
            val r = ReturnStatus("FAIL", e.toString())
            return r.toJsonString()
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawTcp(): String {
        val socket: Socket
        return try {
            socket = Socket(Constants.remoteHttpDomain, 80)
            // Get output stream to send data
            val outputStream = socket.getOutputStream()
            val request =
                "GET / HTTP/1.1\\nHost: " + Constants.remoteHttpDomain + "\\nX-A:msaTest\\n\\n"
            val output = socket.getOutputStream()
            val writer = PrintWriter(output, true)
            writer.print(request)
            socket.close()
            ReturnStatus("OK", "Socket created established.").toJsonString()

        } catch (e: Exception) {
            val r = ReturnStatus("FAIL", e.toString())
            return r.toJsonString()
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rawUdp(): String {
        return try {
            // Create a new UDP socket
            val socket = DatagramSocket()
            val serverAddress = InetAddress.getByName(Constants.remoteHttpDomain)
            val sendData = "HelloUDP".toByteArray()
            val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, 53)

            // Send the UDP packet
            socket.send(sendPacket)
            socket.close()

            ReturnStatus("OK", "Socket created established.").toJsonString()
        } catch (e: Exception) {
            val r = ReturnStatus("FAIL", e.toString())
            return r.toJsonString()
        }
    }
}