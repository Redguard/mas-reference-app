package com.masreferenceapp.privacy

import android.content.Context
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.masreferenceapp.ReturnStatus


class PrivacyMarketingUUID(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "PrivacyMarketingUUID"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getAAID(): String {
        val r = ReturnStatus()
        try{
            val adInfo: AdvertisingIdClient.Info =
                AdvertisingIdClient.getAdvertisingIdInfo(context)

            val isLimitAdTrackingEnabled: Boolean =
                adInfo.isLimitAdTrackingEnabled()

            r.success("Is limited ad tracking enabled : $isLimitAdTrackingEnabled")

            val adId: String? = adInfo.getId()

            r.success("Advertising Id is : $adId")
        }
        catch(e:Exception){
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getAndroidID(): String {
        val r = ReturnStatus()
        try{
            val androidId =  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
            r.success("Android ID is $androidId")
        }
        catch(e:Exception){
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getImei(): String {
        val r = ReturnStatus()
        try{
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imei = telephonyManager.deviceId
            r.success("IMEI is $imei")
        }
        catch(e:Exception){
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getMAC(): String {
        val r = ReturnStatus()
        try{

            val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager?
            val info = manager!!.connectionInfo
            val address = info.macAddress

            r.success("MAC Address is $address")
        }
        catch(e:Exception){
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    //@method
}