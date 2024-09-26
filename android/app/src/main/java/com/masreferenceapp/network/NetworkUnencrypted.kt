package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
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
    fun resolveDns(): String {
        val testDomain = MasSettings.getData("testDomain")
        val r = ReturnStatus()
        try {
            val inetAddress = InetAddress.getByName(testDomain)
            r.addStatus("OK", "Address for the domain '" + testDomain + "' is: " + inetAddress.hostAddress)
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun standardHTTP(): String {
        val testDomain = MasSettings.getData("testDomain")
        val r = ReturnStatus()

        return try {
            val url = URL("http://$testDomain")
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
        val testDomain = MasSettings.getData("testDomain")

        return try {
            val url = URL("http", testDomain, 8080, "/")
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
        val testDomain = MasSettings.getData("testDomain")

        val socket: Socket
        return try {
            socket = Socket(testDomain, 80)
            // Get output stream to send data
            val outputStream = socket.getOutputStream()
            val request =
                "GET / HTTP/1.1\\nHost: " + MasSettings.getData("remoteHttpDomain") + "\\nX-A:msaTest\\n\\n"
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
        val testDomain = MasSettings.getData("testDomain")

        return try {
            // Create a new UDP socket
            val socket = DatagramSocket()
            val serverAddress = InetAddress.getByName(testDomain)
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

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun openHTTP(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sendHTTP(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method

}