package com.example.justcleantask.viewModels.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.data.utils.CheckNetwork
import com.example.justcleantask.callBacks.BaseCallBack
import com.example.justcleantask.services.UploadFavOfflineWorker
import com.mabrouk.loaderlib.RetryCallBack
import java.util.concurrent.TimeUnit

open class BaseViewModel<V:BaseCallBack>(application: Application) : AndroidViewModel(application) ,
    BaseVmodel<V> {
    lateinit var view:V
    fun offline() = !CheckNetwork.isOnline(getApplication())
    fun upload() : LiveData<WorkInfo>{
        val workManager=WorkManager.getInstance(getApplication())
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<UploadFavOfflineWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 20, TimeUnit.SECONDS)
                .build()
        workManager.enqueue(request)
        return workManager.getWorkInfoByIdLiveData(request.id)
    }
    val loader: ObservableBoolean = ObservableBoolean()
    val error: ObservableField<String> = ObservableField()
    val retryCallBack = object : RetryCallBack {
        override fun onRetry() {
            retry()
        }

    }

    open fun retry(){}
    override fun attachView(view: V) {
        this.view=view
    }

}