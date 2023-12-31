package com.example.partyregistration.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["Phone"], unique = true),Index(value = ["Email"], unique = true)])
data class Party (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo("Name")
    var name:String,
    @ColumnInfo("Secretary")
    var secretary:String,
    @ColumnInfo("Phone")
    var phone:String,
    @ColumnInfo("Email")
    var email:String,
    @ColumnInfo("Website")
    var website:String,
    @ColumnInfo("Document")
    var document: String?

) : Parcelable

