package org.masreferenceapp.platform

import android.R.attr.label
import android.R.attr.text
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import androidx.core.content.ContextCompat.getSystemService
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import org.masreferenceapp.SensitiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import java.util.Objects


class PlatformIpc(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
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
    override fun getName(): String {
        return "PlatformIpc"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun exportedActivity(): String {
        val myIntent = Intent()
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        myIntent.setComponent(
            ComponentName(
                "org.masreferenceapp",
                "org.masreferenceapp.platform.helpers.IpcExportedActivityTest"
            )
        )
        myIntent.putExtra("data", "This text has been sent to the activity by the initiator.")
        context.applicationContext.startActivity(myIntent)
        return ReturnStatus(
            "OK",
            "Exported activity started. It contains the following extras: " + Objects.requireNonNull(
                myIntent.extras
            ).toString()
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun service(): String {
        val myIntent = Intent()
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        myIntent.setComponent(
            ComponentName(
                "org.masreferenceapp",
                "org.masreferenceapp.platform.helpers.IpcExportedService"
            )
        )
        myIntent.putExtra("req", "hello service")
        val sc: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                println("onServiceConnected")
            }

            // Called when the connection with the service disconnects unexpectedly.
            override fun onServiceDisconnected(className: ComponentName) {
                println("onServiceDisconnected")
            }
        }
        context.currentActivity!!.bindService(myIntent, sc, Context.BIND_AUTO_CREATE)
        context.currentActivity!!.unbindService(sc)
        return ReturnStatus("OK", "Service started.").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun provider(): String {
        val uri = Uri.parse("content://org.masreferenceapp.ipc.user.provider")
        val values = ContentValues()
        values.put("username", "Max")
        this.currentActivity!!.contentResolver.insert(uri, values)
        val cursor = this.currentActivity!!
            .contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        return ReturnStatus(
            "OK",
            "Provider Started. It contains the following values: " + cursor.columnCount + ", " + cursor.getString(
                0
            )
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun broadcastReceiver(): String {

        val intent = Intent("org.masreferenceapp.DO_SOMETHING").apply {
            putExtra("data", "The initiator sent this data to the BroadCast Receiver. ")
            setPackage("org.masreferenceapp")
        }

        context.sendBroadcast(intent)

        return ReturnStatus(
            "OK",
            "Broadcast received. It contains the following extras: " + Objects.requireNonNull(intent.extras)
                .toString()
        ).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun deepLinks(): String {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("masref://deeplink.activity?param=someData")
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.applicationContext.startActivity(intent)

        return ReturnStatus(
            "OK",
            "Dummy Activity which is accessible by deep link started. Since the Activity finishes immediately, you should not see it."
        ).toJsonString()
    }


    private fun startServerSocket(port: Int) {
        runBlocking {
            launch(Dispatchers.IO) {
                try {
                    val serverSocket = ServerSocket(port)
                    println("Server is listening on localhost:$port")

                    while (true) {
                        val clientSocket = serverSocket.accept()
                        println("Client connected: ${clientSocket.inetAddress}")

                        // Read from client
                        val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                        val receivedMessage = input.readLine()
                        println("Received: $receivedMessage")

                        // Respond to client
                        val output = PrintWriter(clientSocket.getOutputStream(), true)
                        output.println("Message received: $receivedMessage")

                        clientSocket.close()
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun listenLocalhost(): String {

        val localHostIp = InetAddress.getByName("127.0.0.1")
        val localHostDomain = InetAddress.getByName("localhost")


        val localServer1 = ServerSocket(46000)
        val localServer2 = ServerSocket(47000, 1)
        val localServer3 = ServerSocket(48000, 1, localHostIp)
        val localServer4 = ServerSocket(49000, 1, localHostDomain)

        localServer1.close()
        localServer2.close()
        localServer3.close()
        localServer4.close()

        val r = ReturnStatus("OK", "Started listening on localhost and closed server again.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sendLocalhost(): String {

        var url = URL("http://localhost")
        url = URL("http", "localhost", "/")
        url = URL("http", "localhost", 80, "/")

        url = URL("http://127.0.0.1")
        url = URL("http", "127.0.0.1", "/")
        url = URL("http", "127.0.0.1", 80, "/")

        try {
            var rawSocket = Socket("localhost", 8080)
            rawSocket = Socket("127.0.0.1", 8080)
        } catch (_: Exception) {
        }

        val r = ReturnStatus(
            "OK",
            "Created URLs and raw sockets with the intention to connect to localhost"
        )
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeClipboard(): String {
        val r = ReturnStatus()

        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("masData", SensitiveData.data)
        clipboardManager.setPrimaryClip(clipData)
        r.success("Sensitive data written to clipboard using setPrimaryClip(clip).")

        clipboardManager.text = SensitiveData.data
        r.success("Sensitive data written to clipboard using setText(text) (deprecated).")

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readClipboard(): String {
        val r = ReturnStatus()

        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("masData", SensitiveData.data)
        clipboardManager.setPrimaryClip(clipData)

        r.success("Data read from clipboard using primaryClip(). Data ${clipboardManager.primaryClip?.getItemAt(0)?.text?.toString()}")

        clipboardManager.text = SensitiveData.data
        r.success("Data read from clipboard using getText() (deprecated). Data ${clipboardManager.primaryClip?.getItemAt(0)?.text?.toString()}")

        return r.toJsonString()
    }


    //@method

}