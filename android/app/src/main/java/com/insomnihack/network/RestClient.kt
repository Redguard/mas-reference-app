package com.insomnihack.network

import android.util.Base64
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class RestClient(private val secretKey: String) {

    private val baseUrl: String = "http://192.168.0.25:8008/1olm72dnrx/api/v1";

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) // Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Set read timeout
        .build()

    private val jsonMediaType = "application/json; charset=utf-8"

    /* ----------- */

    /**
     * API Key required to send data to the server.
     */
    private fun calculateApiKey (secret: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(secret.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) } // Convert to hex string
    }

    /**
     * Posts the given body into the specified URL using a JSON content-type
     */
    private fun doPostJson (body: RequestBody, url: String, onSuccess: (String) -> Unit, onFailure: (IOException) -> Unit) {

        val apiKey = calculateApiKey(secretKey)

        val headers = Headers.Builder()
            .add("API-KEY", apiKey)
            .add("Content-Type", jsonMediaType)
            .build()

        val request = Request.Builder()
            .url(url)
            .headers(headers)
            .post(body)
            .build()

        httpClient.newCall (request).enqueue (object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CTF", e.toString(), e)
                onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {

                response.use { // Ensures the response body is closed
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string() ?: ""
                        Log.i("CTF", "responseBody: $responseBody")
                        onSuccess(responseBody)
                    } else {
                        onFailure(IOException("HTTP Error: ${response.code} - ${response.message}"))
                    }
                }
            }
        })
    }

    /**
     * Gets an authentication token from the server
     *
     * onSuccess (session_cookie)
     */
    private fun initSession (onSuccess: (String) -> Unit, onFailure: (IOException) -> Unit) {

        val url = "$baseUrl/authorise"

        doPostJson("".toRequestBody(), url, onSuccess, onFailure)
    }

    /**
     * Sends the data from the feedback form
     */
    fun sendFeedback(name: String, feedback: String, onSuccess: (String) -> Unit, onFailure: (IOException) -> Unit) {

        initSession(
            {sessionId ->
                /* It's logged by `doPost` anyways */
                Log.i ("CTF", "Super secret data, or something: $sessionId")

                val url = "$baseUrl/feedback"

                val feedbackB64 = String (Base64.encode(feedback.toByteArray(), Base64.NO_WRAP))

                val requestBody = """
                {
                    "name": "$name",
                    "feedback": "$feedbackB64",
                    "sess_id": "$sessionId"
                }
                """.trimIndent().toRequestBody(jsonMediaType.toMediaType())

                doPostJson (requestBody, url, onSuccess, onFailure)
            },
            onFailure
        )
    }
}