package com.example.dependencyinjectionex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mannual injection
//        val classC = ClassC()
//        classC.startClassC()

//        constructor injection
//        val classC = ClassC()
//        classC.classA = ClassA()
//        classC.classB = ClassB()
//        classC.startClassC()

//        field injection
        val classA = ClassA()
        val classB = ClassB()
        val classC = ClassC(classA,classB)

        DaggerClassCComponent.create().getCInstance().startClassC()
    }
}