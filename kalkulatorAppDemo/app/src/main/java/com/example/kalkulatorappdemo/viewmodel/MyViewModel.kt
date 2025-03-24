package com.example.kalkulatorappdemo.viewmodel

import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    var result: Int = 0

    fun add(n1: Int, n2: Int): MyViewModel {
        result = n1 + n2
        return MyViewModel()
    }
}