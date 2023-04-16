package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.data.model.SensorType
import com.example.glucosereadings.data.repository.SensorRepositoryImpl
import com.example.glucosereadings.domain.repository.SensorRepository

class SetSensorTypeUseCase(
    private val repository: SensorRepository = SensorRepositoryImpl.getInstance()
) {

    operator fun invoke(newType: SensorType) = repository.setSensorType(newType)

}