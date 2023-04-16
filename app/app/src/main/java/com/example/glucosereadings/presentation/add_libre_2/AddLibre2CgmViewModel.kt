package com.example.glucosereadings.presentation.add_libre_2

import androidx.lifecycle.ViewModel
import com.example.glucosereadings.domain.use_case.AddSensorUseCase

class AddLibre2CgmViewModel(
    private val addSensorUseCase: AddSensorUseCase
): ViewModel() {

    fun addSensor() {
        addSensorUseCase()
    }

}