package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepository

class ValidateG6PinUseCase(
    private val repository: SensorRepository = SensorRepository.getInstance()
) {

    operator fun invoke(pin: String) = repository.validateDexcomG6Pin(pin)

}