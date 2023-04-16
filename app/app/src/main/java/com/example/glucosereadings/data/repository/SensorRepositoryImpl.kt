package com.example.glucosereadings.data.repository

import com.example.glucosereadings.data.model.Cgm
import com.example.glucosereadings.data.model.SensorType
import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.domain.repository.SensorRepository
import com.example.glucosereadings.utils.isPinValid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SensorRepositoryImpl private constructor(): SensorRepository {

    private var cgm: Cgm? = null

    private var currentSensorType = SensorType.NONE

    private val sensorType = BehaviorSubject.createDefault<SensorType>(currentSensorType)
    private val sensorState = BehaviorSubject.createDefault<SensorState>(SensorState.NOT_PRESENT)

    companion object {
        private var instance: SensorRepositoryImpl? = null

        fun getInstance(): SensorRepositoryImpl = synchronized(this) {
            if (instance == null) {
                instance = SensorRepositoryImpl()
            }
            instance!!
        }
    }

    override fun validateDexcomG6Pin(pin: String) = isPinValid(pin)

    override fun setSensorType(newType: SensorType) {
        if (newType != currentSensorType) {
            currentSensorType = newType
            sensorType.onNext(currentSensorType)
        }
    }

    override fun addSensor() {
        cgm = Cgm()
        sensorState.onNext(SensorState.PRESENT)
    }

    override fun getSensorEgv(): Observable<Int> {
        return if (cgm != null) {
            cgm!!.startEgvTransmission()
        } else {
            Observable.empty()
        }
    }

    override fun deleteSensor() {
        cgm?.stopEgvTransmission()
        sensorState.onNext(SensorState.NOT_PRESENT)
        cgm = null
    }

    override fun setSensorLimit(limit: Int) {
        cgm?.setEgvLimit(limit)
    }

    override fun getAvailableSensorTypes() = SensorType.values().asList()

    override fun getSensorState() = sensorState

    override fun getSensorType() = sensorType
}