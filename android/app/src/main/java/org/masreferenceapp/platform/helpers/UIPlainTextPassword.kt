package org.masreferenceapp.platform.helpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.masreferenceapp.R

class UIPlainTextPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_text_password_ui)
        this.finish()
    }
}