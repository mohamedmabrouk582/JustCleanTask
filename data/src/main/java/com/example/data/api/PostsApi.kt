package com.example.data.api

import com.example.domain.models.Comment
import com.example.domain.models.Post
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {

    @GET("posts")
    fun requestPosts() : Deferred<ArrayList<Post>>

    @GET("comments")
    fun requestComments(@Query("postId") postId:Int) : Deferred<ArrayList<Comment>>



}