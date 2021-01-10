package com.example.justcleantask.viewModels.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.fav.FavDefaultRepository
import com.example.justcleantask.viewModels.TestUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavRepository : FavDefaultRepository {
    override fun getFavPosts(): LiveData<List<PostEntity>> {
       return MutableLiveData(TestUtils.postsEntity)
    }

    override suspend fun removeFavPost(post: PostEntity) {
         TestUtils.postsEntity.removeIf { post.id==it.id }
        TestUtils.postsEntity.add(post.apply { isFav=false })
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        TestUtils.favOffline.removeIf { post.id==it.id }
    }

    override suspend fun getFavOffline(): Flow<List<FavPostsOffline>> {
       return flow { emit(TestUtils.favOffline)}
    }
}