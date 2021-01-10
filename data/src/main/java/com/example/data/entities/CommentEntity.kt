package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity(
    @PrimaryKey
    val id:Int,
    val postId:Int,
    val name:String?=null,
    val email:String?=null,
    val body:String?=null
)