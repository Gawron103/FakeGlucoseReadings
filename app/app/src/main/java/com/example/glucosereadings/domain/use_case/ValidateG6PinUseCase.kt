package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepositoryImpl
import com.example.glucosereadings.domain.repository.SensorRepository

class ValidateG6PinUseCase(
    private val repository: SensorRepository = SensorRepositoryImpl.getInstance()
) {

    operator fun invoke(pin: String) = repository.validateDexcomG6Pin(pin)

}