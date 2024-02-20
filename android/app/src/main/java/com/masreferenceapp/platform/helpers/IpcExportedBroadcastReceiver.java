package com.masreferenceapp.platform.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IpcExportedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        sb.append("Data: " + intent.getStringExtra("data") + "\n");

        System.out.println(sb.toString());
    }
}