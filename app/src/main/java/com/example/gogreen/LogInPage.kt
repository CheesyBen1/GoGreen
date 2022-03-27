package com.example.gogreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogInPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnRegister: Button = findViewById(R.id.toRegisterBtn)

        btnRegister.setOnClickListener(){
            val intent: Intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }
    }
}