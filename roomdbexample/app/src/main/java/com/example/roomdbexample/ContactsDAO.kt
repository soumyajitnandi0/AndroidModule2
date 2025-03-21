package com.example.roomdbexample

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactsDAO {
    @Insert
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM Contacts")
    fun getContacts():LiveData<List<Contact>>

    @Update
    suspend fun updateContact(contact: Contact)
}