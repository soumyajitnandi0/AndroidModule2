package com.example.dependencyinjectionex

import dagger.Module
import dagger.Provides

@Module
object ClassABModule {
    @Provides
    fun provideClassA(): ClassA {
        return ClassA()
    }

    @Provides
    fun provideClassB(): ClassB {
        return ClassB()
    }
}