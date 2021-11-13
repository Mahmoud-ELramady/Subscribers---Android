package com.example.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo.db.SubscriberRepositary
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(private val repo: SubscriberRepositary):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscribeViewModel::class.java)){
            return SubscribeViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}