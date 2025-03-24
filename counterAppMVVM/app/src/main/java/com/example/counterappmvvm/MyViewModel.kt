package com.example.counterappmvvm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel(){
    private var counter = 0;

    fun getInitialCounter():Int{
        return counter
    }

    fun incrementCounter():Int{
        return counter++
    }
    fun decrementCounter():Int{
        if (counter>0){
            counter--
        }
        return counter
    }
}