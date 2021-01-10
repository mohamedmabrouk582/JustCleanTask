package com.example.domain.models

data class Comment(
    val id:Int,
    val postId:Int,
    val name:String?=null,
    val email:String?=null,
    val body:String?=null
)