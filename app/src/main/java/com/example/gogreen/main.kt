package com.example.gogreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.gogreen.ui.main.SectionsPagerAdapter
import com.example.gogreen.databinding.ActivityMain2Binding
import com.example.gogreen.fragments.activitiesFragment
import com.example.gogreen.fragments.adapters.ViewPageAdapter
import com.example.gogreen.fragments.donationFragment
import com.example.gogreen.fragments.newsFragment
import com.example.gogreen.models.userLogged

class main : AppCompatActivity() {


    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        if(userLogged.currentUser == null){
            finish()
            Toast.makeText(baseContext, "Logged out!",
                Toast.LENGTH_SHORT).show()
        }
     binding = ActivityMain2Binding.inflate(layoutInflater)
     setContentView(binding.root)


        setUpTabs()

        val btnProfile: ImageButton = findViewById(R.id.btnProfile)

        btnProfile.setOnClickListener(){
            val intent: Intent = Intent(this, profile::class.java)
            startActivity(intent)
        }


    }

    public fun end(){
        finish()
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


    override fun onBackPressed() {

    }
}