package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ActivitiesMainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_main_page)


        val tvTest: TextView = findViewById(R.id.tvTest)

        tvTest.text = intent.getStringExtra("email").toString()
    }
}