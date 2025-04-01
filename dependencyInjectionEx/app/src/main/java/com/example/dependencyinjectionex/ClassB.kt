package com.example.dependencyinjectionex

import android.util.Log
import javax.inject.Inject

class ClassB @Inject constructor(){
    fun startClassB(){
        Log.i("tag2","ClassB started")
    }
}   