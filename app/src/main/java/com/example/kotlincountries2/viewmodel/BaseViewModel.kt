package com.example.kotlincountries2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job() //yapılacak bir iş

    override val coroutineContext: CoroutineContext     //coroutine i artık kullanabiliriz
        get() = job + Dispatchers.Main  //önce işini yap(job), sonra main thread e dön

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}