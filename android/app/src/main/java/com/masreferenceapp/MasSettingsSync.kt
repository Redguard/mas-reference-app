package com.masreferenceapp

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.json.JSONObject

class MasSettingsSync(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "MasSettingsSync"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setSettings(settings: String): String {
        val settingsObject: JSONObject = JSONObject(settings)

        MasSettings.setData("testDomain", settingsObject.get("testDomain").toString())
        MasSettings.setData("canaryToken", settingsObject.get("canaryToken").toString())
        MasSettings.setData("androidApiKey", settingsObject.get("androidApiKey").toString())

        return "MAS Settings updated."
    }

}