package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gogreen.databinding.ActivityDonationHistoryBinding
import com.example.gogreen.fragments.adapters.DonationRecycleAdapter
import com.example.gogreen.fragments.adapters.ViewPageAdapter
import com.example.gogreen.models.DonoHistory
import com.example.gogreen.models.userLogged.donationList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DonationHistory : AppCompatActivity(), DonationRecycleAdapter.OnItemClickListener {

    private lateinit var auth :FirebaseAuth
    private lateinit var binding: ActivityDonationHistoryBinding



    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://assignmentauth-1112b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UsersDB")






        val myAdapter = DonationRecycleAdapter(donationList, this)

        binding.donationRecyclerView.adapter = myAdapter
        binding.donationRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.donationRecyclerView.setHasFixedSize(true)



    }

    override fun itemClick(position: Int){
        val selectedStudent = donationList[position]


    }

    override fun onBackPressed() {

    }
}