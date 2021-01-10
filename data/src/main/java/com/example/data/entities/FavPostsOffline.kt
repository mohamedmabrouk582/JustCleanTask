package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavPostsOffline(
    @PrimaryKey
    val id:Int,
    val userId:Int,
    val title:String?=null,
    val body:String?=null)