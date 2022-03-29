package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NavUtils
import com.example.gogreen.models.userLogged

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var loggedUser = userLogged.currentUser

        var email:TextView = findViewById(R.id.test)

        if (loggedUser != null) {
            email.text=loggedUser.email.toString()
        }

        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener(){
            userLogged.currentUser = null

             NavUtils.navigateUpFromSameTask(this)
        }

    }
    override fun onBackPressed() {

    }

}