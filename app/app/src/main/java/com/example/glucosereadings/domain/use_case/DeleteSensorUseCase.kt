package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.repository.SensorRepositoryImpl
import com.example.glucosereadings.domain.repository.SensorRepository

class DeleteSensorUseCase(
    private val repository: SensorRepository = SensorRepositoryImpl.getInstance()
) {

    operator fun invoke() = repository.deleteSensor()

}