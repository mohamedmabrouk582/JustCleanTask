package com.example.justcleantask.viewModels.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.comment.CommentsDefaultRepository
import com.example.data.utils.toEntity
import com.example.domain.models.Comment
import com.example.domain.utils.Result
import com.example.justcleantask.viewModels.TestUtils
import kotlinx.coroutines.flow.Flow

class FakeCommentsRepository : CommentsDefaultRepository {
    override fun getSavedComments(post_Id: Int): LiveData<List<CommentEntity>> {
        return MutableLiveData(TestUtils.comments.toEntity())
    }

    override suspend fun addPostFav(post: PostEntity) {
        TestUtils.postsEntity.add(post)
    }

    override suspend fun removePostFav(post: PostEntity) {
        TestUtils.postsEntity.removeIf { post.id==it.id }
        TestUtils.postsEntity.add(post.apply { isFav=false })
    }

    override suspend fun saveOfflineFav(post: FavPostsOffline) {
        TestUtils.favOffline.add(post)
    }

    override suspend fun removeOfflineFav(post: FavPostsOffline) {
        TestUtils.favOffline.removeIf { post.id==it.id }
    }

    override suspend fun requestComments(postId: Int): Flow<Result<ArrayList<Comment>>> {
        return TestUtils.getComments()
    }

    override suspend fun saveComments(comments: ArrayList<Comment>) {
        TestUtils.comments.addAll(comments)
    }
}