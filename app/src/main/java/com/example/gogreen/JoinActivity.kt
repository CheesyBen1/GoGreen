package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.userLogged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")

        var tvName: TextView = findViewById(R.id.tvJoinsName)
        var tvDate: TextView = findViewById(R.id.tvJoinedDate)
        var tvTime: TextView = findViewById(R.id.tvJoinedTime)
        var tvHost: TextView = findViewById(R.id.tvJoinedHost)
        var tvLocation: TextView = findViewById(R.id.tvJoinedLocation)
        var tvDescription: TextView = findViewById(R.id.tvJoinedDescription)

        var activityJoin: Activitys? = userLogged.joinedActivity

        tvName.text = activityJoin!!.Name
        tvDate.text = activityJoin!!.Date
        tvTime.text = activityJoin!!.Time
        tvHost.text = activityJoin!!.Host
        tvLocation.text = activityJoin!!.Location
        tvDescription.text = activityJoin!!.Description


        val btnJoin: Button = findViewById(R.id.btnJoinActivity)

        btnJoin.setOnClickListener(){
            addActivity(activityJoin)
            finish()
        }

    }

    fun addActivity(activity: Activitys){

        database.child("userTable").child(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener { user->
                var count = 0
                count = user.child("joinedCount").value.toString().toInt()
                count++
                database.child("userTable").child(auth.currentUser?.uid.toString()).child("joinedCount").setValue(count)
                database.child("userTable").child(auth.currentUser?.uid.toString()).child("activityHistory").child(count.toString()).setValue(activity)
                Toast.makeText(baseContext, "Join successful!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
            }


    }
}