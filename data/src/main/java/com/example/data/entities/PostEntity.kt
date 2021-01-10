package com.example.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PostEntity(
    @PrimaryKey
    val id:Int,
    val userId:Int,
    val title:String?=null,
    val body:String?=null,
    var isFav:Boolean=false
): Parcelable