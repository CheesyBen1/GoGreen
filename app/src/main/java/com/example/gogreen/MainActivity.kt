package com.example.gogreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnA : Button = findViewById(R.id.btnA)

        btnA.setOnClickListener(){
            val intent : Intent = Intent(this, SignUpPage::class.java)

            startActivity(intent)
        }
    }
}