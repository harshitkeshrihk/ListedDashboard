package com.hk.listeddashboard.Utils

import timber.log.Timber

sealed class ResultHandler<out T> {
    class Success<out T>(val result: T) : ResultHandler<T>() {
        init {
            Timber.d("Success: $result")
        }
    }
    object Loading : ResultHandler<Nothing>()
    class Failure(exception: Throwable, val message: String = exception.message.toString()) : ResultHandler<Nothing>(){
        init {
            Timber.d("Error: $exception")
        }
    }
}