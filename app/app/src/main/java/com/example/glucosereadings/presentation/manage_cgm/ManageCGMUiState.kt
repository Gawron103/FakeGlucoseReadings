package com.example.glucosereadings.presentation.manage_cgm

import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.data.model.SensorType

data class ManageCGMUiState(
    val sensorState: SensorState = SensorState.NOT_PRESENT,
    val sensorType: SensorType = SensorType.NONE,
)
