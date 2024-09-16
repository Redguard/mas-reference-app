package com.masreferenceapp

import org.json.JSONArray
import org.json.JSONObject

class ReturnStatus {

    private val statuses = mutableListOf<Status>()

    data class Status(val statusCode: String?, val message: String?) {
        fun toJson(): JSONObject {
            val jo = JSONObject()
            jo.putOpt("statusCode", statusCode)
            jo.putOpt("message", message)
            return jo
        }
    }

    constructor()

    // Constructor for initializing with one status
    constructor(statusCode: String?, message: String?) {
        statuses.add(Status(statusCode, message))
    }

    fun addStatus(statusCode: String?, message: String?) {
        statuses.add(Status(statusCode, message))
    }

    fun getStatuses(): List<Status> {
        return statuses
    }

    fun toJsonString(): String {
        return try {
            val array = JSONArray()
            for (status in statuses) {
                array.put(status.toJson())
            }
            array.toString()
        } catch (e: Exception) {
            val errorJo = JSONObject()
            errorJo.put("error", "An error occurred while creating the JSON response.")
            errorJo.put("exception", e.toString())
            errorJo.toString()
        }
    }
}