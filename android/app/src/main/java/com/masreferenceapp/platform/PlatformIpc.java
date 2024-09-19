package com.masreferenceapp.platform;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.Status;

import java.security.Provider;
import java.util.Objects;


public class PlatformIpc extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public PlatformIpc(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

//    /** Defines callbacks for service binding, passed to bindService(). */
//    private ServiceConnection connection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                                       IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance.
//            LocalBinder binder = (LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
//        }
//    };


    @NonNull
    @Override
    public String getName() {
        return "PlatformIpc";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String exportedActivity(){

        Intent myIntent = new Intent();
        myIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        myIntent.setComponent(new ComponentName("com.masreferenceapp", "com.masreferenceapp.platform.helpers.IpcExportedActivityTest"));
        myIntent.putExtra("key", "Hello from the other side.");

        context.getApplicationContext().startActivity(myIntent);
        return new ReturnStatus("OK", "Exported activity started. It contains the following extras: " + Objects.requireNonNull(myIntent.getExtras()).toString()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String service(){

        Intent myIntent = new Intent();
        myIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        myIntent.setComponent(new ComponentName("com.masreferenceapp", "com.masreferenceapp.platform.helpers.IpcExportedService"));
        myIntent.putExtra("req", "hello service");

        ServiceConnection sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                System.out.println("onServiceConnected");
            }

            // Called when the connection with the service disconnects unexpectedly.
            @Override
            public void onServiceDisconnected(ComponentName className) {
                System.out.println("onServiceDisconnected");
            }

        };

        context.getCurrentActivity().bindService(myIntent, sc, Context.BIND_AUTO_CREATE);
        context.getCurrentActivity().unbindService(sc);

        return new ReturnStatus("OK", "Service started.").toJsonString();
    }




    @ReactMethod(isBlockingSynchronousMethod = true)
    public String provider(){

        Uri uri = Uri.parse("content://com.masreferenceapp.ipc.user.provider");

        ContentValues values = new ContentValues();
        values.put("username", "Max");
        this.getCurrentActivity().getContentResolver().insert(uri, values);

        Cursor cursor = this.getCurrentActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        return new ReturnStatus("OK", "Provider Started. It contains the following values: " + cursor.getColumnCount() + ", " + cursor.getString(0)).toJsonString();


    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String broadcastReceiver(){

        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.masreferenceapp", "com.masreferenceapp.platform.helpers.IpcExportedBroadcastReceiver");
        intent.setComponent(componentName);
        intent.putExtra("data", "Nothing to see here, move along.");
        this.context.getCurrentActivity().sendBroadcast(intent);

        return new ReturnStatus("OK", "Broadcast received. It contains the following extras: " + Objects.requireNonNull(intent.getExtras()).toString()).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String deepLinks(){

        return new ReturnStatus("OK", "MESSAGE").toJsonString();

    }
}