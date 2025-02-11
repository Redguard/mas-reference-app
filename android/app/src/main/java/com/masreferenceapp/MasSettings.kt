package com.masreferenceapp

object MasSettings {
    private val data = mutableMapOf<String, String>()

    @JvmStatic
    fun setData(key: String, value: String) {
        data[key] = value
    }

    @JvmStatic
    fun getData(key: String): String? {
        return data[key]
    }

    @JvmStatic
    fun getTestDomain(): String {
        return data["testDomain"].toString()
    }

    @JvmStatic
    fun getCanaryToken(): String {
        return data["canaryToken"].toString()
    }

    @JvmStatic
    fun getAndroidCloudProjectNumber(): Long {
        return data["androidCloudProjectNumber"]?.toLong() ?: 0
    }

}

