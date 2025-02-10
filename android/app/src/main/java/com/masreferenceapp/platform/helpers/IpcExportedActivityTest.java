package com.masreferenceapp.platform.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.masreferenceapp.R;

public class IpcExportedActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_exported_test);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        // can be null
        String callerPackage = intent.getPackage();

        System.out.println(value);
        System.out.println(callerPackage);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("return", "String data");
        setResult(Activity.RESULT_OK, resultIntent);

        this.finish();

    }
}