package com.masreferenceapp.resilience;

import android.content.Context;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


public class ResilienceAntiVm extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public ResilienceAntiVm(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "ResilienceAntiVm";
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getImsi() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = telephonyManager.getSubscriberId();

            return new ReturnStatus("OK", "IMSI: " + imsi).toJsonString();
        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBuild() {
        String BOARD = android.os.Build.BOARD;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;

        return new ReturnStatus("OK", "Information about the build: " + BOARD + ", " + BRAND + ", " + DEVICE + ", " + MODEL + ", " + PRODUCT).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getNetworkInterface() {

        StringBuilder status = new StringBuilder();
        ReturnStatus r = new ReturnStatus();

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();

                r.addStatus("OK", "Network interface: " + intf.getName());
            }

        } catch (Exception e) {
            return new ReturnStatus("FAIL", e.toString()).toJsonString();
        }

        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallerPackageName() {

        ReturnStatus r = new ReturnStatus();


        StringBuilder status = new StringBuilder();
        PackageManager packageManager = context.getPackageManager();
        String PACKAGE_NAME = context.getPackageName();
        // In theory, if the package installer does not throw an exception, package exists

        try {
            String packageVar1 = packageManager.getInstallerPackageName(PACKAGE_NAME);
            r.addStatus("OK", "Package Name: " + packageVar1);


        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }

        try {
            InstallSourceInfo packageVar2 = packageManager.getInstallSourceInfo(PACKAGE_NAME);
            r.addStatus("[OK]", "Package Name: " + packageVar2);


        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSensor() {
        try {
            SensorManager smanger = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = smanger.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            List<Sensor> sensor2 = smanger.getSensorList(Sensor.TYPE_ALL);

            return new ReturnStatus("OK", "Sensor queried. Sensor info: " + sensor.getName() + ", " + Arrays.toString(sensor2.toArray())).toJsonString();

        } catch (Exception e) {
            return new ReturnStatus("FAIL", "Exception: " + e).toJsonString();
        }
    }


}