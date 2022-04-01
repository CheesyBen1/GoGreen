package com.example.gogreen.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class postModel:ViewModel() {

    val likesTotal: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }
}