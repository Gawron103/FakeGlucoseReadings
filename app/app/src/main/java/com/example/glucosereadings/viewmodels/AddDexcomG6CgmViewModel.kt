package com.example.glucosereadings.viewmodels

import androidx.lifecycle.ViewModel
import com.example.glucosereadings.repositories.SensorRepository

class AddDexcomG6CgmViewModel(
    private val repository: SensorRepository
): ViewModel() {

    fun addSensor(pin: String): Boolean {
        if (validatePinEntry(pin)) {
            repository.addSensor()
            return true
        }

        return false
    }

    private fun validatePinEntry(pin: String) = repository.validateDexcomG6Pin(pin)

}