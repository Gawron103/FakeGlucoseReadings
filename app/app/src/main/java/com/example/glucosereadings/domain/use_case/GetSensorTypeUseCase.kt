package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepository

class GetSensorTypeUseCase(
    private val repository: SensorRepository = SensorRepository.getInstance()
) {

    operator fun invoke() = repository.sensorType

}