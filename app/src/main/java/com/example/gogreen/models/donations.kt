package com.example.gogreen.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class donations : ViewModel() {

    val donationTotal: MutableLiveData<Double> by lazy{
        MutableLiveData<Double>()
    }


}