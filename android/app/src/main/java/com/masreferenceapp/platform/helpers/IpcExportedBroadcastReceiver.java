package com.masreferenceapp.platform.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IpcExportedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String sb = "Action: " + intent.getAction() + "\n" +
                "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n" +
                "Data: " + intent.getStringExtra("data") + "\n";

        System.out.println(sb);
    }
}