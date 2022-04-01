package com.example.gogreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import com.example.gogreen.databinding.ActivityMain2Binding
import com.example.gogreen.fragments.activitiesFragment
import com.example.gogreen.fragments.adapters.PostRecycleAdapter
import com.example.gogreen.fragments.adapters.ViewPageAdapter
import com.example.gogreen.fragments.donationFragment
import com.example.gogreen.fragments.newsFragment
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.DonoHistory
import com.example.gogreen.models.Posts
import com.example.gogreen.models.userLogged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Main : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityMain2Binding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        if(auth.currentUser == null){
            finish()
            Toast.makeText(baseContext, "Logged out!",
                Toast.LENGTH_SHORT).show()
        }
     binding = ActivityMain2Binding.inflate(layoutInflater)
     setContentView(binding.root)


            if(auth.currentUser!= null)updateJoined()
        updatePosts()

       // updateActivities()



        setUpTabs()

        val btnProfile: ImageButton = findViewById(R.id.btnProfile)

        btnProfile.setOnClickListener(){
            val intent: Intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        var activitiesColor = resources.getIntArray(R.array.activitiesColors)

        userLogged.activitiesColors = activitiesColor.toTypedArray()



    }



    private fun setUpTabs(){
        val adapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(donationFragment(),"Donations")
        adapter.addFragment(activitiesFragment(),"Activities")
        adapter.addFragment(newsFragment(),"News Feed")

        val viewPager:ViewPager = binding.viewPager
        viewPager.adapter = adapter

        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)


    }

    private fun updateJoined(){
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")


        database.child("userTable").child(auth.currentUser?.uid.toString())
            .child("joinedCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                userLogged.joinedList.clear()
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

    private fun updatePosts(){
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("PostsDB")
        database.child("currentCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                userLogged.postList.clear()
                for (i in donoCount downTo 1) {
                    database.child(i.toString()).get()
                        .addOnSuccessListener {
                            var name = it.child("name").value.toString()
                            var time = it.child("time").value.toString()
                            var caption = it.child("caption").value.toString()
                            var likes = it.child("likes").value.toString().toInt()

                            userLogged.postList.add(Posts(name,caption, time, likes))

                        }
                }

            }
    }

    private fun updateActivities(){
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ActivitiesDB")
        database.child("currentCount").get()
            .addOnSuccessListener { donateCount ->
                var donoCount: Int = 0
                donoCount = donateCount.value.toString().toInt()
                userLogged.activityList.clear()
                for (i in donoCount downTo 1) {
                    database.child(i.toString()).get()
                        .addOnSuccessListener {
                            var name = it.child("name").value.toString()
                            var date = it.child("date").value.toString()
                            var time = it.child("time").value.toString()
                            var location = it.child("location").value.toString()
                            var desc = it.child("description").value.toString()
                            var host = it.child("host").value.toString()

                            userLogged.activityList.add(Activitys(name,date,time,location,desc,host))

                        }
                }

            }
    }

    override fun onBackPressed() {

    }
}