package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.domain.models.Comment
import com.example.domain.models.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun savePosts(posts:ArrayList<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveComments(comments:ArrayList<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun favOffline(post: FavPostsOffline)

    @Delete
    suspend fun removeFavOffline(post: FavPostsOffline)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostAsFav(post:PostEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun removeFavPost(post:PostEntity)

    @Query("select * from postentity ")
    fun getSavedPosts() : LiveData<List<PostEntity>>

    @Query("select * from postentity where isFav=1")
    fun getFavPosts() : LiveData<List<PostEntity>>

    @Query("select * from commententity where postId=:id")
    fun getPostComments(id:Int) : LiveData<List<CommentEntity>>

    @Query("select * from FavPostsOffline")
    fun getFavOffline() : Flow<List<FavPostsOffline>>

}