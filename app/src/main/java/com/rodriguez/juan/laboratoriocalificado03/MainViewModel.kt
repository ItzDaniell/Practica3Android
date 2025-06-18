package com.rodriguez.juan.laboratoriocalificado03

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    val teacherList = MutableLiveData<List<TeacherResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorApi = MutableLiveData<String>()

    init {
        getAllTeachers()
    }

    private fun getAllTeachers() {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(TeacherApi::class.java).getTeachers()

                if (call.isSuccessful) {
                    call.body()?.let { response ->
                        isLoading.postValue(false)
                        teacherList.postValue(response.teachers)
                    } ?: run {
                        isLoading.postValue(false)
                        errorApi.postValue("Response body is null")
                    }
                } else {
                    isLoading.postValue(false)
                    errorApi.postValue("Error: ${call.code()} - ${call.message()}")
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                errorApi.postValue("Network error: ${e.message}")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}