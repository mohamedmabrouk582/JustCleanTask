package com.example.justcleantask.services

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.repository.posts.PostRepository
import com.example.data.utils.toDomain
import com.example.domain.models.Post
import kotlinx.coroutines.flow.collect

class UploadFavOfflineWorker @WorkerInject constructor(@Assisted val context: Context, @Assisted params: WorkerParameters
                                                       , val repository: PostRepository ) : CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        var result:String?=null
        repository.getFavOffline().collect {
             it.forEach {
                 repository.removeOfflineFav(it)
                 result=uploadtoServer(it.toDomain())
             }
        }
        return if (result==null)Result.success() else Result.failure()
    }

   suspend fun uploadtoServer(post:Post) : String?{
        Log.d("UploadIng","Upload ... $post" )
       return null
     }
}