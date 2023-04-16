package com.example.glucosereadings.presentation.cgm_simulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.GetSensorStateUseCase
import com.example.glucosereadings.domain.use_case.SetSensorEgvLimitUseCase

class CgmSimulatorViewModelFactory constructor(
    private val setCgmEgvLimitUseCase: SetSensorEgvLimitUseCase,
    private val getSensorStateUseCase: GetSensorStateUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CgmSimulatorViewModel::class.java) -> {
                CgmSimulatorViewModel(
                    setCgmEgvLimitUseCase,
                    getSensorStateUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}