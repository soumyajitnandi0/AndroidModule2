package com.example.authusingroomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: UserData)

    @Query("SELECT * FROM UserData WHERE name = :name AND password = :password")
    suspend fun getUser(name: String, password: String): UserData?

    @Query("SELECT * FROM UserData WHERE name = :name")
    suspend fun getUserByName(name: String): UserData?

    @Query("SELECT * FROM UserData")
    suspend fun getAllUsers(): List<UserData>
}