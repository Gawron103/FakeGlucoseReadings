package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepository

class SetSensorEgvLimitUseCase(
    private val repository: SensorRepository = SensorRepository.getInstance()
) {

    operator fun invoke(limit: Int) = repository.setSensorLimit(limit)

}