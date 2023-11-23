package com.example.partyregistration.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PartyDao {
    @Insert
    fun createParty(party:Party)

    @Query("SELECT * FROM Party")
    fun getAllParties():List<Party>

    @Update
    fun updateParty(party: Party)

    @Delete
    fun deleteParty(party: Party)

    @Query("SELECT COUNT(*) FROM Party WHERE Name = :name OR Email = :email OR Phone = :phone")
    fun checkDuplicate(name: String, email: String, phone: String): Int
}