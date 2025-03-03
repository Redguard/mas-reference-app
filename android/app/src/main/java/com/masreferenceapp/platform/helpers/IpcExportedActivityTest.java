package com.masreferenceapp.platform.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.masreferenceapp.R;

public class IpcExportedActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_exported_test);

        Intent intent = getIntent();
        String value = intent.getStringExtra("data");

        // can be null
        String callerPackage = intent.getPackage();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("return", "Data sent back to the intent initiator.");
        setResult(Activity.RESULT_OK, resultIntent);

        Log.i("MAS", "Activity called via ICP. The intent contained the following data: " + value);

        this.finish();

    }
}