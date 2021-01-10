package com.example.data.repository.fav

import androidx.lifecycle.LiveData
import com.example.data.db.PostsDao
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavRepository @Inject constructor(val dao:PostsDao) : FavDefaultRepository {
    override fun getFavPosts(): LiveData<List<PostEntity>> {
        return dao.getFavPosts()
    }

    override suspend fun removeFavPost(post: PostEntity) {
        dao.removeFavPost(post)
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        dao.removeFavOffline(post)
    }

    override suspend fun getFavOffline(): Flow<List<FavPostsOffline>> {
        return dao.getFavOffline()
    }
}