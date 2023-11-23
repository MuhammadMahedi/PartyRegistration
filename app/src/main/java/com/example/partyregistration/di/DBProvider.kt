package com.example.partyregistration.di

import android.content.Context
import com.example.partyregistration.data.PartyDao
import com.example.partyregistration.data.PartyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBProvider {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): PartyDatabase {

        return PartyDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDao(db:PartyDatabase):PartyDao{

        return db.getPartyDao()
    }

}