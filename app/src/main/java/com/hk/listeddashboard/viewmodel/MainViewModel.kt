package com.hk.listeddashboard.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hk.listeddashboard.api.NetworkService
import com.hk.listeddashboard.models.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: NetworkService
): ViewModel() {


    private val _data = MutableLiveData<UserResponse>()
    val mdata: LiveData<UserResponse> = _data

    val mlinkType = MutableLiveData<String?>(null)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        if(_data.value==null){
            fetchDataFromApi()
        }
    }

    fun fetchDataFromApi() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    apiService.getData()
                }
                Log.d("MainViewModel",data.body().toString())
                _data.value = data.body()
            } catch (e: Exception) {
                // Handle any exceptions here
            }
        }
    }

    fun selectType(type: String?){
        mlinkType.value = type
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel() // Cancel all coroutines when ViewModel is cleared
    }


}