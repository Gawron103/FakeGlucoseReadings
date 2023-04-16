package com.example.glucosereadings.presentation.manage_cgm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.DeleteSensorUseCase
import com.example.glucosereadings.domain.use_case.GetSensorInfoUseCase

class ManageCgmViewModelFactory constructor(
    private val getSensorInfoUseCase: GetSensorInfoUseCase,
    private val deleteSensorUseCase: DeleteSensorUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ManageCgmViewModel::class.java) -> {
                ManageCgmViewModel(
                    getSensorInfoUseCase,
                    deleteSensorUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }
}