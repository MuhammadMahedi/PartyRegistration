package com.example.partyregistration.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Party::class], version = 1)
abstract class PartyDatabase : RoomDatabase(){
    abstract fun getPartyDao():PartyDao

    companion object{
        private var db:PartyDatabase?=null
        fun getInstance(context: Context): PartyDatabase{
            if(db==null){
                db= Room.databaseBuilder(context,
                    PartyDatabase::class.java,
                    "party_table")
                    .allowMainThreadQueries()
                    .build()

                return db as PartyDatabase
            }else{
                return db as PartyDatabase
            }
        }
    }
}