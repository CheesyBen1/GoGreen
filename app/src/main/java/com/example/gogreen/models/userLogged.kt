package com.example.gogreen.models

import com.google.firebase.auth.FirebaseUser

object userLogged {
    var currentUser: FirebaseUser? = null
    var donationList = mutableListOf<DonoHistory>()
    var activityList = mutableListOf<Activitys>()

    var activitiesColors = emptyArray<Int>()

    var joinedActivity: Activitys? = null
    var joinedList = mutableListOf<Activitys>()

    var joinedDetail: Activitys? = null


    var postList = mutableListOf<Posts>()


}