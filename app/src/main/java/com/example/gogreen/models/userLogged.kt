package com.example.gogreen.models

import com.google.firebase.auth.FirebaseUser

object userLogged {
    var currentUser: FirebaseUser? = null
    var donationList = mutableListOf<DonoHistory>()
}