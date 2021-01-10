package com.example.justcleantask.callBacks

import androidx.lifecycle.LiveData
import com.example.data.entities.PostEntity

interface PostsCallBack : BaseCallBack {
    fun loadSavedPosts(posts:LiveData<List<PostEntity>>)
    fun noNetwork(error:String)
}