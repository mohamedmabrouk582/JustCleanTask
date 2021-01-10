package com.example.data.repository.posts

import android.content.Context
import android.media.MediaParser
import androidx.lifecycle.LiveData
import com.example.data.api.PostsApi
import com.example.data.db.PostsDao
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.utils.executeCall
import com.example.data.utils.toEntity
import com.example.domain.models.Post
import com.example.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(@ApplicationContext val  context: Context,val api:PostsApi, val dao:PostsDao) :
    PostsDefaultRepository {

    override fun getSavedPosts(): LiveData<List<PostEntity>> {
        return dao.getSavedPosts()
    }

    override suspend fun removeFavPost(post: PostEntity) {
        dao.removeFavPost(post)
    }

    override suspend fun addPostFav(post: PostEntity) {
        dao.addPostAsFav(post)
    }

    override suspend fun saveOfflineFav(post: FavPostsOffline) {
      dao.favOffline(post)
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        dao.removeFavOffline(post)
    }

    override fun getFavOffline(): Flow<List<FavPostsOffline>> {
       return  dao.getFavOffline()
    }


    override suspend fun requestAllPosts(): Flow<Result<ArrayList<Post>>> =
        executeCall(context){api.requestPosts()}

    override suspend fun savePosts(posts: ArrayList<Post>) {
       dao.savePosts(posts.toEntity())
    }

}