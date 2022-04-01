package com.example.gogreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.gogreen.models.Activitys
import com.example.gogreen.models.userLogged

class joinedDetails : AppCompatActivity() {

    var currentActivity: Activitys? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var tvName: TextView = findViewById(R.id.tvJoinsName)
        var tvDate: TextView = findViewById(R.id.tvJoinsDate)
        var tvTime: TextView = findViewById(R.id.tvJoinsTime)
        var tvHost: TextView = findViewById(R.id.tvJoinsHost)
        var tvLocation: TextView = findViewById(R.id.tvJoinsLocation)
        var tvDescription: TextView = findViewById(R.id.tvJoinsDescription)

        var activityJoin: Activitys? = userLogged.joinedDetail

        tvName.text = activityJoin!!.Name
        tvDate.text = activityJoin!!.Date
        tvTime.text = activityJoin!!.Time
        tvHost.text = activityJoin!!.Host
        tvLocation.text = activityJoin!!.Location
        tvDescription.text = activityJoin!!.Description
    }


}