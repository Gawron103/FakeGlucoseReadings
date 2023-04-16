package com.example.glucosereadings.presentation.add_dexcom_g6

import androidx.lifecycle.ViewModel
import com.example.glucosereadings.data.repository.SensorRepository
import com.example.glucosereadings.domain.use_case.AddSensorUseCase
import com.example.glucosereadings.domain.use_case.ValidateG6PinUseCase

class AddDexcomG6CgmViewModel(
    private val addSensorUseCase: AddSensorUseCase,
    private val validateG6PinUseCase: ValidateG6PinUseCase
): ViewModel() {

    fun addSensor(pin: String): Boolean {
        if (validatePinEntry(pin)) {
            addSensorUseCase()
            return true
        }

        return false
    }

    private fun validatePinEntry(pin: String) = validateG6PinUseCase(pin)

}