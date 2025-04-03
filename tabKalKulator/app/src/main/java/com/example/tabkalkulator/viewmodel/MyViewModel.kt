package com.example.tabkalkulator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tabkalkulator.model.CalcData

class MyViewModel:ViewModel() {
    fun add(n1:Float,n2:Float):CalcData{
        return CalcData(n1,n2,(n1+n2).toString())
    }
    fun sub(n1:Float,n2:Float):CalcData{
        return CalcData(n1,n2,(n1-n2).toString())
    }
    fun mul(n1:Float,n2:Float):CalcData{
        return CalcData(n1,n2,(n1*n2).toString())
    }
    fun divide(n1:Float,n2:Float):CalcData{
        return CalcData(n1,n2,(n1/n2).toString())
    }
}