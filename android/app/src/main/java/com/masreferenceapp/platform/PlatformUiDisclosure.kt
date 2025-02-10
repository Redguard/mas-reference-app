package com.masreferenceapp.platform

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData


class PlatformUiDisclosure(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "PlatformUiDisclosure"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sensitiveDataNotifications(): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                currentActivity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1
                    )
                }
            }
        }

        val channelId = "mas_ref_channel"
        val channelName = "MAS Reference App Channel"
        val description = "This is the notification channel for the MAS Reference App"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            this.description = description
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)


        val notificationId = 1

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("MAS Reference App")
            .setContentText(SensitiveData.data)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)


//        val myIntent = Intent()
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        myIntent.setComponent(
//            ComponentName(
//                "com.masreferenceapp",
//                "com.masreferenceapp.platform.helpers.UINotification"
//            )
//        )
//
//        context.applicationContext.startActivity(myIntent)

        return ReturnStatus(
            "OK",
            "Dummy Activity which sends sensitive data to the UI using notifications started. Since the Activity finishes immediately, you should not see it."
        ).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun prohibitScreenshot(): String {
        val myIntent = Intent()
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        myIntent.setComponent(
            ComponentName(
                "com.masreferenceapp",
                "com.masreferenceapp.platform.helpers.UIProhibitScreenshot"
            )
        )

        context.applicationContext.startActivity(myIntent)
        return ReturnStatus(
            "OK",
            "Dummy Activity with plaintext fields intended for passwords started. Since the Activity finishes immediately, you should not see it."
        ).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun passwordPlaintextUi(): String {

        val myIntent = Intent()
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        myIntent.setComponent(
            ComponentName(
                "com.masreferenceapp",
                "com.masreferenceapp.platform.helpers.UIPlainTextPassword"
            )
        )

        context.applicationContext.startActivity(myIntent)
        return ReturnStatus(
            "OK",
            "Dummy Activity which prohibits screenshots started. Since the Activity finishes immediately, you should not see it."
        ).toJsonString()
    }


    //@method
}