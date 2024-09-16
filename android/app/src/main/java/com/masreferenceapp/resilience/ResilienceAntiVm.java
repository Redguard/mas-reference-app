package com.masreferenceapp.resilience;

import android.content.pm.ApplicationInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Debug;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.Status;

import java.net.NetworkInterface;
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
    public String getImsi(){
        try{
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            String imsi = telephonyManager.getSubscriberId();
            return Status.status("OK", "Return Value: " + imsi);
        }
        catch (Exception e){
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e.toString());
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBuild(){
            String BOARD = android.os.Build.BOARD;
            String BRAND = android.os.Build.BRAND;
            String DEVICE = android.os.Build.DEVICE;
            String MODEL = android.os.Build.MODEL;
            String PRODUCT = android.os.Build.PRODUCT;

            return Status.status("OK", "Return Value: " + BOARD + ", " + BRAND + ", " + DEVICE + ", " + MODEL + ", " + PRODUCT );
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getNetworkInterface(){

        StringBuilder message = new StringBuilder();
        StringBuilder status = new StringBuilder();

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();

                message.append(intf.getName() + ", ");
                status.append("[OK]");
            }

        } catch (Exception e) {
            message.append(e.toString());
            status.append("[FAIL]");
        }

        return Status.status(status.toString(), message.toString());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallerPackageName(){

        StringBuilder message = new StringBuilder();
        StringBuilder status = new StringBuilder();
        PackageManager packageManager = context.getPackageManager();
        String PACKAGE_NAME = context.getPackageName();
        // In theory, if the package installer does not throw an exception, package exists

        try {
            String packageVar1 = packageManager.getInstallerPackageName(PACKAGE_NAME);
            message.append(packageVar1);
            status.append("[OK]");

        } catch (Exception e) {
            message.append(e.toString());
            status.append("[FAIL]");
        }

        try {
            InstallSourceInfo packageVar2 = packageManager.getInstallSourceInfo(PACKAGE_NAME);
            message.append(packageVar2.getInstallingPackageName());
            status.append("[OK]");

        } catch (Exception e) {
            message.append(e.toString());
            status.append("[FAIL]");
        }
        return Status.status(status.toString(), message.toString());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSensor(){
        try{
             SensorManager smanger = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
             Sensor sensor = smanger.getDefaultSensor(Sensor.TYPE_ORIENTATION);
             List<Sensor> sensor2 = smanger.getSensorList(Sensor.TYPE_ALL);

            return Status.status("OK", "Return Value: " + sensor.getName() + ", " + sensor2.toArray().toString());
        }
        catch (Exception e){
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e.toString());
return r.toJsonString();
        }
    }


}