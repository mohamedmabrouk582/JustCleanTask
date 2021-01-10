package com.example.justcleantask.viewModels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.data.repository.fav.FavDefaultRepository
import com.example.data.utils.toOffline
import com.example.justcleantask.callBacks.FavCallBack
import com.example.justcleantask.viewModels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavViewModel<V:FavCallBack> @ViewModelInject constructor(app:Application,val repository: FavDefaultRepository) : BaseViewModel<V>(app)  {

    fun loadFavPosts()=repository.getFavPosts()


    suspend fun removeFav(post:PostEntity){
            repository.removeFavPost(post)
    }

    suspend fun removeOfflineFav(post:FavPostsOffline){
        repository.removeOfflineFav(post)
    }

}