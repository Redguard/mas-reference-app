package com.masreferenceapp.storage

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData
import kotlinx.coroutines.MainScope


class StorageLog(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    private fun logToAll(message: String) {
        val tag = "MasReferenceAppTestLog"
        Log.v(tag, message)
        Log.d(tag, message)
        Log.i(tag, message)
        Log.w(tag, message)
        Log.e(tag, message)
    }

    override fun getName(): String {
        return "StorageLog"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logName(): String {
        val r = ReturnStatus()
        val messages: MutableList<String> = ArrayList()
        messages.add("Name: test")
        messages.add("Name:test")
        messages.add("name: test")
        messages.add("name:   test")
        messages.add("First Name: test")
        messages.add("Last Name: test")
        messages.add("Vorname: test")
        messages.add("Vorname:test")
        messages.add("Nachname: test")
        for (m in messages) {
            logToAll(m)
            r.addStatus("OK", "Logged the following message:$m")

        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logPassword(): String {
        val r = ReturnStatus()
        val messages: MutableList<String> = ArrayList()
        messages.add("Password: test")
        messages.add("password: test")
        messages.add("Passwd: test")
        messages.add("passwd: test")
        messages.add("pwd: test")
        messages.add("Pwd: test")
        messages.add("PWD: test")
        messages.add("Secret: test")
        messages.add("secret: test")
        messages.add("PIN: test")
        messages.add("pin: test")
        messages.add("Passphrase: test")
        messages.add("passphrase: test")
        messages.add("passphrase:   test")
        messages.add("PassPhrase: test")
        messages.add("Pass Phrase: test")
        messages.add("Pass Phrase:test")
        messages.add("Private Key: test")
        messages.add("PrivateKey: test")
        messages.add("Secret Key: test")
        messages.add("SecretKey: test")
        for (m in messages) {
            logToAll(m)
            r.addStatus("OK", "Logged the following message:$m")
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logPhoneNumber(): String {
        val r = ReturnStatus()
        val messages: MutableList<String> = ArrayList()
        messages.add("PhoneNumber: +41763452345")
        messages.add("phoneNumber: +41 (076) 3452345")
        messages.add("phoneNumber:+41 (076) 3452345")
        messages.add("phone: +41 (076) 3452345")
        messages.add("phone number: +41 (076) 3452345")
        messages.add("Phone Number: +41 (076) 3452345")
        for (m in messages) {
            logToAll(m)
            r.addStatus("OK", "Logged the following message:$m")

        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logEmail(): String {
        val r = ReturnStatus()
        val messages: MutableList<String> = ArrayList()
        messages.add("eMail: fakeuser1@gmail.com")
        messages.add("e-Mail: fakeuser1@gmail.com")
        messages.add("e Mail: fakeuser1@gmail.com")
        messages.add("e mail: fakeuser1@gmail.com")
        messages.add("EMail: fakeuser1@gmail.com")
        messages.add("EMAIL:fakeuser1@gmail.com")
        messages.add("EMAIL:    fakeuser1@gmail.com")
        messages.add("EMAIL: fakeuser1@gmail.com")
        messages.add("fakeuser2@yahoo.com")
        messages.add("fakeuser4@hotmail.com")
        messages.add("fakeuser_11@bluewin.ch")
        messages.add("fakeuser16@protonmail.ch")
        for (m in messages) {
            logToAll(m)
            r.addStatus("OK", "Logged the following message:$m")

        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logFinData(): String {
        val r = ReturnStatus()
        val messages: MutableList<String> = ArrayList()
        messages.add("CC: 5425233430109903")
        messages.add("CC: 4929 1234 5678 9012")
        messages.add("Credit Card: 4929 1234 5678 9012")
        messages.add("IBAN: GB82WEST12345698765432")
        messages.add("IBAN: GB82 WEST 1234 5698 7654 32")
        messages.add("IBAN: DE89370400440532013000")
        messages.add("IBAN: DE89 3704 0044 0532 0130 00")
        messages.add("IBAN: FR1420041010050500013M02606")
        messages.add("IBAN: FR14 2004 1010 0505 0001 3M02 606")
        messages.add("IBAN: ES9121000418450200051332")
        messages.add("IBAN: ES91 2100 0418 4502 0005 1332")
        messages.add("IBAN: IT60X0542811101000000123456")
        messages.add("IBAN: IT60 X054 2811 1010 0000 1234 56")
        for (m in messages) {
            logToAll(m)
            r.addStatus("OK", "Logged the following message:$m")

        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logLocation(): String {
        val r = ReturnStatus("OK", "Android Stub")
        return r.toJsonString()
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logCanaryToken(): String {
        val r = ReturnStatus()
        logToAll(MasSettings.getCanaryToken())

        return ReturnStatus("OK", "Canary Token logged: " + MasSettings.getCanaryToken()).toJsonString()

    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun logSensitiveData(): String {
        val r = ReturnStatus()
        logToAll(SensitiveData.data)

        return ReturnStatus("OK", "Sensitive Data logged: " + SensitiveData.data).toJsonString()
    }

    //@method

}