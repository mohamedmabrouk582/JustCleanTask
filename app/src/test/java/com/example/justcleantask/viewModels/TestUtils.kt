package com.example.justcleantask.viewModels

import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.domain.models.Comment
import com.example.domain.models.Post
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.flow

object TestUtils {
    val posts = arrayListOf(Post(1,1),Post(2,1),Post(3,1))
    val comments = arrayListOf(Comment(1,1),Comment(2,1),Comment(3,1),)
    val postsEntity= arrayListOf(PostEntity(1,1,isFav = true), PostEntity(2,1,isFav = true),PostEntity(3,1))
    val favOffline = arrayListOf(FavPostsOffline(1,1),FavPostsOffline(2,1),FavPostsOffline(3,1))

    fun getPosts()=
        flow {
            emit(Result.OnSuccess(posts))
        }

    fun getComments() =
        flow {
            emit(Result.OnSuccess(comments))
        }
}