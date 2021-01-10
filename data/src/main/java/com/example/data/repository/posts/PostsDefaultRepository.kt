package com.example.data.repository.posts

import androidx.lifecycle.LiveData
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.domain.models.Post
import com.example.domain.repository.PostsRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface PostsDefaultRepository : PostsRepository  {
    fun getSavedPosts() : LiveData<List<PostEntity>>
    suspend fun removeFavPost(post:PostEntity)
    suspend  fun addPostFav(post:PostEntity)
    suspend fun saveOfflineFav(post:FavPostsOffline)
    suspend fun removeOfflineFav(post:FavPostsOffline)
    fun getFavOffline() : Flow<List<FavPostsOffline>>
}