package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity

@Database(entities = [PostEntity::class,CommentEntity::class,FavPostsOffline::class],version = 2,exportSchema = false)
abstract class PostsDb : RoomDatabase(){
    abstract fun getDao() : PostsDao
}