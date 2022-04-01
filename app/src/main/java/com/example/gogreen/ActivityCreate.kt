package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gogreen.models.Activitys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ActivityCreate : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var databaseU: DatabaseReference

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth= Firebase.auth
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ActivitiesDB")
        databaseU = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        val textName: EditText = findViewById(R.id.textActCreateName)
        val textDate: EditText = findViewById(R.id.textActCreateDate)
        val textTime: EditText = findViewById(R.id.textActCreateTime)
        val textLocation: EditText = findViewById(R.id.textActCreateLoc)
        val textDesc: EditText = findViewById(R.id.textActCreateDesc)



        val btnCreate: Button = findViewById(R.id.btnCreateAct)

        btnCreate.setOnClickListener() {
            var actName: String = ""
            var actDate: String = ""
            var actTime: String = ""
            var actLoc: String = ""
            var actDesc: String = ""
            var host: String = ""

            actName = textName.text.toString()
            actDate = textDate.text.toString()
            actTime = textTime.text.toString()
            actLoc = textLocation.text.toString()
            actDesc = textDesc.text.toString()

            databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("username")
                .get()
                .addOnSuccessListener {
                    var host = it.child("username").value.toString()
                    createNewAct(Activitys(actName, actDate, actTime, actLoc, actDesc, host))

                }

            finish()
//            databaseU.child("userTable").child(auth.currentUser?.uid.toString()).get()
//                .addOnSuccessListener {host->
//                    var host = host.child("username").value.toString()
//
//                     database.get()
//                         .addOnSuccessListener {
//                             var currentCount:Int = 0
//                             currentCount = it.child("currentCount").toString().toInt()
//                             createNewAct(Activitys(actName, actDate, actTime, actLoc, actDesc, host), currentCount+1)
//                             database.child("currentCount").setValue(currentCount + 1)
//                         }
//
//
//                    Toast.makeText(baseContext, "Activity created successfully!",
//                        Toast.LENGTH_SHORT).show()
//                    finish()
//
//                }

//            database.get()
//                .addOnSuccessListener { counter->
//                    count = counter.child("currentCount").toString().toInt()
//                    count++
//                    database.child("currentCount").setValue(count)
//
//                }
//
//            databaseU.child("userTable").child(auth.currentUser?.uid.toString()).child("username").get()
//                .addOnSuccessListener {
//                    var host = it.child("username").value.toString()
//                    createNewAct(Activitys(actName, actDate, actTime, actLoc, actDesc, host), count)
//
//                    Toast.makeText(baseContext, "Activity created successfully!",
//                        Toast.LENGTH_SHORT).show()
//                    finish()
//                }
//
//        }
        }
    }

    private fun createNewAct(newAct: Activitys) {


        database.get()
            .addOnSuccessListener { counter ->
                count = counter.child("currentCount").toString().toInt()
                count++
                database.child("currentCount").setValue(count)

            }

        database.child(count.toString()).setValue(newAct)
        Toast.makeText(
            baseContext, "Activity created successfully!",
            Toast.LENGTH_SHORT
        ).show()
//        database.child(count.toString()).setValue(newAct)
    }




}