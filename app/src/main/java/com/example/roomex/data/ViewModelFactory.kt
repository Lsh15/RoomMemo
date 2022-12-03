package com.example.roomex.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomex.data.respository.MemoRepository

class ViewModelFactory(private val memoRepository: MemoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MemoRepository::class.java).newInstance(memoRepository)
    }
}