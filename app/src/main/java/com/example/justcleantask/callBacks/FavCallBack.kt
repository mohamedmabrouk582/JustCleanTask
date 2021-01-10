package com.example.justcleantask.callBacks

import androidx.lifecycle.LiveData
import com.example.data.entities.PostEntity

interface FavCallBack : BaseCallBack {
    fun loadFavPosts(fav:LiveData<List<PostEntity>>)
}