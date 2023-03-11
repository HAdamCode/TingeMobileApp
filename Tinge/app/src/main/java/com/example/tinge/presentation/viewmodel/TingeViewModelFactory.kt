package com.example.tinge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.example.tinge.data.TingeRepo

class TingeViewModelFactory(context: Context): ViewModelProvider.NewInstanceFactory() {
    companion object {
        private const val LOG_TAG = "448.TingeViewModelFactory"
    }
    val currentContext = context
    fun getViewModelClass() = TingeViewModel::class.java

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(LOG_TAG, "create() called")
        if(modelClass.isAssignableFrom(getViewModelClass())) {
            Log.d(LOG_TAG, "creating ViewModel: ${getViewModelClass()}")
            return modelClass
                .getConstructor(TingeRepo::class.java)
                .newInstance(TingeRepo.getInstance(context = currentContext))
        }
        Log.e(LOG_TAG, "Unknown ViewModel: $modelClass")
        throw IllegalArgumentException("Unknown ViewModel")
    }
}