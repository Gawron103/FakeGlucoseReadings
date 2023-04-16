package com.example.glucosereadings.presentation.switch_cgm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.GetAvailableSensorTypesUseCase
import com.example.glucosereadings.domain.use_case.GetSensorTypeUseCase
import com.example.glucosereadings.domain.use_case.SetSensorTypeUseCase

class SwitchCgmViewModelFactory constructor(
    private val setSensorTypeUseCase: SetSensorTypeUseCase,
    private val getAvailableSensorTypesUseCase: GetAvailableSensorTypesUseCase,
    private val getSensorTypeUseCase: GetSensorTypeUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SwitchCgmViewModel::class.java) -> {
                SwitchCgmViewModel(
                    setSensorTypeUseCase,
                    getAvailableSensorTypesUseCase,
                    getSensorTypeUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}