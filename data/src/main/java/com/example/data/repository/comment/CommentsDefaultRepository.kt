package com.example.data.repository.comment

import androidx.lifecycle.LiveData
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.domain.repository.CommentsRepository

interface CommentsDefaultRepository : CommentsRepository {
    fun getSavedComments(post_Id:Int) : LiveData<List<CommentEntity>>
    suspend fun addPostFav(post:PostEntity)
    suspend fun removePostFav(post: PostEntity)
    suspend fun saveOfflineFav(post: FavPostsOffline)
    suspend fun removeOfflineFav(post: FavPostsOffline)
}