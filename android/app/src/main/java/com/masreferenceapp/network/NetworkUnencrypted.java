package com.masreferenceapp.network;

import android.net.DnsResolver;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.SingleThreadAsserter;
import com.masreferenceapp.Constants;
import com.masreferenceapp.Status;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class NetworkUnencrypted extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public NetworkUnencrypted(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "NetworkUnencrypted";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String resolveDns(){

        // Create a new instance of DnsResolver
        DnsResolver resolver = DnsResolver.getInstance();

        Executor mExecutor = Executors.newSingleThreadExecutor();
        CancellationSignal mcancellationSignal = new CancellationSignal();
        DnsResolver.Callback callback = new DnsResolver.Callback() {
            @Override
            public void onAnswer(@NonNull Object answer, int rcode) {
                System.out.println(answer.toString());
            }

            @Override
            public void onError(@NonNull DnsResolver.DnsException error) {

            }
        };

        // Resolve the domain name to its IP addresses
        resolver.query(null, Constants.remoteWebViewHttpDomain, DnsResolver.TYPE_A, DnsResolver.FLAG_NO_CACHE_LOOKUP, mExecutor, mcancellationSignal, callback);

        return Status.status("OK", resolver.toString());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String standardHTTP(){
        try{
            URL url = new URL("http://" + Constants.remoteWebViewHttpDomain);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            StringBuilder response = new StringBuilder();
            urlConnection.disconnect();
            return Status.status("OK", urlConnection.getResponseMessage());
        }
        catch (Exception e){
            return Status.status("FAIL", e.toString());
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String nonStandardHTTP(){
        try{
            URL url = new URL("http", Constants.remoteWebViewHttpDomain, Constants.remoteWebViewHttpPort, "/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.disconnect();
            return Status.status("OK", urlConnection.getResponseMessage());
        }
        catch (Exception e){
            return Status.status("FAIL", e.toString());
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String rawTcp(){

        Socket socket;
        try{
            socket = new Socket(Constants.remoteWebViewHttpDomain, 80);
            // Get output stream to send data
            OutputStream outputStream = socket.getOutputStream();
            String request = "GET / HTTP/1.1\\nHost: " + Constants.remoteWebViewHttpDomain + "\\nX-A:msaTest\\n\\n";

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.print(request);
            socket.close();


            return Status.status("OK", socket.toString());

        }
        catch (Exception e){
            return Status.status("FAIL", "Failed to init socket.");


        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String rawUdp(){
        try {
            // Create a new UDP socket
            DatagramSocket socket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName(Constants.remoteWebViewHttpDomain);
            byte[] sendData = "HelloUDP".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 53);

            // Send the UDP packet
            socket.send(sendPacket);
            socket.close();
            return Status.status("OK",  socket.toString());
        } catch (Exception e) {
            return Status.status("FAIL", "Failed to init socket.");
        }
    }


}