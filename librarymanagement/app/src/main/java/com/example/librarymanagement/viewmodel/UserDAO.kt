package com.example.librarymanagement.viewmodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.librarymanagement.model.UserData

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: UserData)

    @Query("SELECT * FROM UserData WHERE userName = :userName AND password = :password")
    suspend fun getUser(userName: String, password: String): UserData?

    @Query("SELECT * FROM UserData WHERE userName = :userName")
    suspend fun getUserByUserName(userName: String): UserData?

    @Query("SELECT * FROM UserData")
    suspend fun getAllUsers(): List<UserData>

    @Query("UPDATE UserData SET email = :email, description = :description WHERE userName = :userName")
    suspend fun updateUserData(userName: String, email: String, description: String)

    @Query("SELECT email, description FROM UserData WHERE userName = :userName")
    suspend fun getUserData(userName: String): List<UserData>

    @Query("UPDATE UserData SET password = :password WHERE userName = :userName")
    suspend fun changePassword(userName: String, password: String)
}