package com.example.data.repository.fav

import androidx.lifecycle.LiveData
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import kotlinx.coroutines.flow.Flow

interface FavDefaultRepository {
    fun getFavPosts() : LiveData<List<PostEntity>>
    suspend fun removeFavPost(post: PostEntity)
    suspend fun removeOfflineFav(post: FavPostsOffline)
    suspend fun getFavOffline() : Flow<List<FavPostsOffline>>
}