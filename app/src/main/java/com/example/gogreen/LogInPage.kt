package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LogInPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}