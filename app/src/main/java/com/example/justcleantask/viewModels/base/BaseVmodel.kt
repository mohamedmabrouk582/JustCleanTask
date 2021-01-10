package com.example.justcleantask.viewModels.base

import com.example.justcleantask.callBacks.BaseCallBack

interface BaseVmodel<V : BaseCallBack> {
    fun attachView(view:V)
}