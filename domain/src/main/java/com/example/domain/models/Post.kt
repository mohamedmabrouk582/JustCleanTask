package com.example.domain.models

data class Post(
    val id:Int,
    val userId:Int,
    val title:String?=null,
    val body:String?=null
    )