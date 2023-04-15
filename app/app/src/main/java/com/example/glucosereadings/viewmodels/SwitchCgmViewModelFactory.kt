package com.example.glucosereadings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.repositories.SensorRepository

class SwitchCgmViewModelFactory constructor(
    private val repository: SensorRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SwitchCgmViewModel::class.java) -> {
                SwitchCgmViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}