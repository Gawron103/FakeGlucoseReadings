package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepository

class GetAvailableSensorTypesUseCase(
    private val repository: SensorRepository = SensorRepository.getInstance()
) {

    operator fun invoke() = repository.availableSensorTypes

}