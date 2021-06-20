package com.example.redpacktest.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.redpacktest.api.Repo

class VMFactory(private val repo: Repo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}