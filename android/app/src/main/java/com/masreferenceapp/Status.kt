package com.masreferenceapp

import org.json.JSONObject

object Status {
    @JvmStatic
    @Deprecated("Use the new ReturnStatus() class.")
    fun status(statusCode: String?, message: String?): String {
        return try {
            val jo = JSONObject()
            jo.put("statusCode", statusCode)
            jo.put("message", message)
            jo.toString()
        } catch (e: Exception) {
            e.toString()
        }
    }
}
