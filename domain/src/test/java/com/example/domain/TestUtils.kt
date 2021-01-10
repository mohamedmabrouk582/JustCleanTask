package com.example.domain

import com.example.domain.models.Comment
import com.example.domain.models.Post
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.flow

object TestUtils {
    val posts = arrayListOf(Post(1,1),Post(2,1),Post(3,1))
    val comments = arrayListOf(Comment(1,1),Comment(2,1),Comment(3,1))
    val favPosts= arrayListOf(Post(1,1),Post(2,1))

    fun getPosts()=
        flow {
            emit(Result.OnSuccess(posts))
        }

    fun getComments() =
        flow {
            emit(Result.OnSuccess(comments))
        }
}