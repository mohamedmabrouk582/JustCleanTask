package com.example.justcleantask.viewModels.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.posts.PostsDefaultRepository
import com.example.domain.models.Post
import com.example.domain.utils.Result
import com.example.justcleantask.viewModels.TestUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePostsRepository : PostsDefaultRepository {
    override fun getSavedPosts(): LiveData<List<PostEntity>> {
        return MutableLiveData(TestUtils.postsEntity)
    }

    override suspend fun removeFavPost(post: PostEntity) {
        TestUtils.postsEntity.removeIf { post.id==it.id }
        TestUtils.postsEntity.add(post.apply { isFav=false })
    }

    override suspend fun addPostFav(post: PostEntity) {
        TestUtils.postsEntity.add(post)
    }

    override suspend fun saveOfflineFav(post: FavPostsOffline) {
        TestUtils.favOffline.add(post)
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        TestUtils.favOffline.removeIf { post.id==it.id }
    }

    override fun getFavOffline(): Flow<List<FavPostsOffline>> {
        return flow { emit(TestUtils.favOffline) }
    }

    override suspend fun requestAllPosts(): Flow<Result<ArrayList<Post>>> {
        return TestUtils.getPosts()
    }

    override suspend fun savePosts(posts: ArrayList<Post>) {
        TestUtils.posts.addAll(posts)
    }
}