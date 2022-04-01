package com.example.gogreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gogreen.fragments.adapters.ActivitiesRecycleAdapter
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.DonoHistory
import com.example.gogreen.models.Posts
import com.example.gogreen.models.userLogged
import com.example.gogreen.models.userLogged.activityList
import com.example.gogreen.models.userLogged.donationList
import com.example.gogreen.models.userLogged.joinedDetail
import com.example.gogreen.models.userLogged.joinedList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Profile : AppCompatActivity(),  ActivitiesRecycleAdapter.OnItemClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        database =
            FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("UsersDB")

        var tvLoggedNamed: TextView = findViewById(R.id.tvLoggedName)
        var tvLoggedEmail: TextView = findViewById(R.id.tvLoggedEmail)

        if (auth.currentUser != null) {
            database.child("userTable").child(auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    tvLoggedNamed.text = it.child("username").value.toString()
                }
                .addOnFailureListener {
                    tvLoggedNamed.text = it.message
                }

            database.child("userTable").child(auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    tvLoggedEmail.text = it.child("email").value.toString()
                }
                .addOnFailureListener {
                    tvLoggedEmail.text = it.message
                }
        }

        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener() {
            auth.signOut()
            userLogged.currentUser = null
            NavUtils.navigateUpFromSameTask(this)
        }


        val tvDonoTotal: TextView = findViewById(R.id.tvDonoAmount)


        database.child("userTable").child(auth.currentUser?.uid.toString()).child("donationCount")
            .get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                var donoTotal = 0.0
                donoCount = donateCount.value.toString().toInt()
                for (i in 1..donoCount) {
                    database.child("userTable").child(auth.currentUser?.uid.toString())
                        .child("donationHistory").child(i.toString()).get()
                        .addOnSuccessListener {

                            donoTotal += it.child("amount").value.toString().toDouble()
                            tvDonoTotal.text = String.format("%.2f", donoTotal)
                        }

                }
            }
            .addOnFailureListener {
                tvDonoTotal.text = "No history found!"
            }




        database.child("userTable").child(auth.currentUser?.uid.toString())
            .child("donationCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                donationList.clear()
                for (i in donoCount downTo 1) {
                    database.child("userTable").child(auth.currentUser?.uid.toString())
                        .child("donationHistory").child(i.toString()).get()
                        .addOnSuccessListener {
                            var amount = it.child("amount").getValue<Double>()
                            var time = it.child("time").value.toString()

                            donationList.add(DonoHistory(amount!!, time))

                        }
                }

            }

       updateJoined()


        val myAdapter = ActivitiesRecycleAdapter(userLogged.joinedList, this)
        val mainHandler = Handler(Looper.getMainLooper())
        val myRecycler: RecyclerView = findViewById(R.id.joinedRecycler)

        myRecycler.adapter = myAdapter
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)



        val donation: View = findViewById(R.id.donationConstraint)

        donation.setOnClickListener() {

            val intent: Intent = Intent(this, DonationHistory::class.java)
            startActivity(intent)
        }




    }

    override fun itemClick(position: Int){
        val selectedActivity = userLogged.joinedList[position]

        joinedDetail = selectedActivity

        val intent: Intent = Intent(this, joinedDetails::class.java)
        startActivity(intent)

    }



    private fun updateJoined(){
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")


        database.child("userTable").child(auth.currentUser?.uid.toString())
            .child("joinedCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                joinedList.clear()
                for (i in donoCount downTo 1) {
                    database.child("userTable").child(auth.currentUser?.uid.toString())
                        .child("activityHistory").child(i.toString()).get()
                        .addOnSuccessListener {
                            var name = it.child("name").value.toString()
                            var date = it.child("date").value.toString()
                            var time = it.child("time").value.toString()
                            var location = it.child("location").value.toString()
                            var desc = it.child("description").value.toString()
                            var host = it.child("host").value.toString()

                            userLogged.joinedList.add(Activitys(name,date,time,location,desc,host))

                        }
                }

            }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateJoined()
    }


}