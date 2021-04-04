package com.vikram.doordashlite.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikram.doordashlite.repo.DoorDashRepository

/**
 *   Created by vikram.gupta on 4/4/21.
 */
class MainViewModelFactory(private val repository: DoorDashRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repository::class.java).newInstance(repository)
    }
}