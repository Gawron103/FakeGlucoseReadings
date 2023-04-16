package com.example.glucosereadings.domain.repository

import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.data.model.SensorType
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface SensorRepository {

    fun validateDexcomG6Pin(pin: String): Boolean

    fun setSensorType(newType: SensorType)

    fun addSensor()

    fun getSensorEgv(): Observable<Int>

    fun deleteSensor()

    fun setSensorLimit(limit: Int)

    fun getAvailableSensorTypes(): List<SensorType>

    fun getSensorState(): BehaviorSubject<SensorState>

    fun getSensorType(): BehaviorSubject<SensorType>
}