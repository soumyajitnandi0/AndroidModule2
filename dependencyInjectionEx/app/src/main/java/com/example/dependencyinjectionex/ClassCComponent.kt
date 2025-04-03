package com.example.dependencyinjectionex

import dagger.Component

@Component(modules = [ClassABModule::class])
interface ClassCComponent {
    fun getCInstance():ClassC
}