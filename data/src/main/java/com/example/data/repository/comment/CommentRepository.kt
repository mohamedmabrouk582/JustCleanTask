package com.example.data.repository.comment

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.data.api.PostsApi
import com.example.data.db.PostsDao
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.utils.executeCall
import com.example.data.utils.toEntity
import com.example.domain.models.Comment
import com.example.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentRepository @Inject constructor(@ApplicationContext val context: Context , val api:PostsApi, val dao:PostsDao) : CommentsDefaultRepository{

    override fun getSavedComments(post_Id: Int): LiveData<List<CommentEntity>> {
        return  dao.getPostComments(post_Id)
    }

    override suspend fun addPostFav(post: PostEntity) {
            dao.addPostAsFav(post)
    }

    override suspend fun removePostFav(post: PostEntity) {
        dao.removeFavPost(post)
    }

    override suspend fun requestComments(postId: Int): Flow<Result<ArrayList<Comment>>> {
        return executeCall(context){api.requestComments(postId)}
    }

    override suspend fun saveComments(comments: ArrayList<Comment>) {
            dao.saveComments(comments.toEntity())
    }

    override suspend fun saveOfflineFav(post: FavPostsOffline) {
        dao.favOffline(post)
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        dao.removeFavOffline(post)
    }
}