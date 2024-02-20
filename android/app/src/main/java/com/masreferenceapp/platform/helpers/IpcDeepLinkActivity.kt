package com.masreferenceapp.platform.helpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.masreferenceapp.R


class IpcDeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc_deep_link)

        val intent = intent
        val action = intent.action
        val data = intent.data

        println(intent.toString())
        println(action.toString())
        println(data.toString())
        finish();
    }
}