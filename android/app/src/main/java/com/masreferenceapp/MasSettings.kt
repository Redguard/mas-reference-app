package com.masreferenceapp

object MasSettings {
    private val data = mutableMapOf<String, String>()
//    private var remoteHttpDomain: String? = null
//    private var remoteHttpsDomain: String? = null
//    private var canaryToken: String = null
//    private var remoteHttpPort: Int = null


    @JvmStatic
    fun setData(key: String, value: String) {
        data[key] = value
    }

    @JvmStatic
    fun getData(key: String): String? {
        return data[key]
    }


}

