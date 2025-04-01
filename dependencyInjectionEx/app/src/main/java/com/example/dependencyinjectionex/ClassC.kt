package com.example.dependencyinjectionex

import android.util.Log
import javax.inject.Inject

//class ClassC {
//    private val classA = ClassA()
//    private val classB = ClassB()
//
//    fun startClassC(){
//        classA.startClassA()
//        classB.startClassB()
//        Log.i("tag3","ClassC started")
//    }
//}

//class ClassC(){
//    lateinit var classA: ClassA
//    lateinit var classB: ClassB
//
//    fun startClassC(){
//        classA.startClassA()
//        classB.startClassB()
//        Log.i("tag3","ClassC started")
//    }
//}

class ClassC @Inject constructor(private val classA: ClassA, private val classB: ClassB){
    fun startClassC(){
        classA.startClassA()
        classB.startClassB()
        Log.i("tag3","ClassC started")
    }
}