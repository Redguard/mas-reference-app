package org.masreferenceapp.platform.helpers;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class IpcExportedService extends Service {

    // Binder given to clients.
    private final IBinder binder = new IpcExportedBinder();
    // Random number generator.
    private final Random mGenerator = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("A client connected to this service.");

        System.out.println(intent.getPackage());
        System.out.println(intent.getStringExtra("req"));

        return binder;
    }

    /**
     * Method for clients.
     */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class IpcExportedBinder extends Binder {
        IpcExportedService getService() {
            // Return this instance of LocalService so clients can call public methods.
            return IpcExportedService.this;
        }
    }

}