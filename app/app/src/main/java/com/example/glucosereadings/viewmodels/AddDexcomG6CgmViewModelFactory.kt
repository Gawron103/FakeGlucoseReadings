package com.example.glucosereadings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.repositories.SensorRepository

class AddDexcomG6CgmViewModelFactory constructor(
    private val repository: SensorRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddDexcomG6CgmViewModel::class.java) -> {
                AddDexcomG6CgmViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}