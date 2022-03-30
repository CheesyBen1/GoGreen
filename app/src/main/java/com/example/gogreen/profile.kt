package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NavUtils
import com.example.gogreen.models.userLogged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        var loggedUser =  ""

        var tvLoggedNamed:TextView = findViewById(R.id.tvLoggedName)

        if (auth.currentUser != null) {
            database.child("userTable").child(auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    tvLoggedNamed.text = it.child("username").value.toString()
                }
                .addOnFailureListener {
                    tvLoggedNamed.text = it.message
                }
        }

        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener(){
            auth.signOut()
            userLogged.currentUser = null
             NavUtils.navigateUpFromSameTask(this)
        }

    }
    override fun onBackPressed() {

    }

}