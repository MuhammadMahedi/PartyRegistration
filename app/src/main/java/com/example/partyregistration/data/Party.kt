package com.example.partyregistration.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["Phone"], unique = true),Index(value = ["Email"], unique = true)])
data class Party(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo("Name")
    var name:String,
    @ColumnInfo("Secretary")
    var sercetary:String,
    @ColumnInfo("Phone")
    var phone:String,
    @ColumnInfo("Email")
    var email:String,
    @ColumnInfo("Website")
    var website:String

) : Parcelable
