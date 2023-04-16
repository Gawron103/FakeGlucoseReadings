package com.example.glucosereadings.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.GetSensorEgvUseCase

class HomeViewModelFactory constructor(
    private val getSensorEgvUseCase: GetSensorEgvUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(getSensorEgvUseCase) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}