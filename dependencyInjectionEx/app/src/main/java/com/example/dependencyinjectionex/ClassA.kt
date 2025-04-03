package com.example.dependencyinjectionex

import android.util.Log
import javax.inject.Inject

class ClassA @Inject constructor() {
    fun startClassA(){
        Log.i("tag1","ClassA started")
    }
}