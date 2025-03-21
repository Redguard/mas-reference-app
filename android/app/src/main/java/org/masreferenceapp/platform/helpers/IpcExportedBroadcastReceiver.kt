package org.masreferenceapp.platform.helpers

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.IntentSanitizer

class IpcExportedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {


        // TODO USE CASe Intent filter
//        val cleanIntent = IntentSanitizer.Builder()
//            .allowExtra("data", String.Companion::class.java)
//            .allowAction("org.masreferenceapp.DO_SOMETHING")
//            .allowType("text/plain")
//            .build()
//            .sanitizeByThrowing(intent);

        val intentData = """
            Action: ${intent.action}\n
            URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n
            Data: ${intent.getStringExtra("data")}
            """

        Log.i("MAS", "IpcExportedBroadcastReceiver got the following data from the initiator: $intentData" )

    }
}