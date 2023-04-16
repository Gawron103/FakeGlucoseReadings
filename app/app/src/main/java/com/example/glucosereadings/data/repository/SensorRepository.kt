package com.example.glucosereadings.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.data.model.Cgm
import com.example.glucosereadings.data.model.SensorType
import com.example.glucosereadings.data.model.SensorState
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SensorRepository private constructor() {

    private var cgm: Cgm? = null
    private var egvDisposable: Disposable? = null

    private var currentSensorType = SensorType.NONE

    val sensorType = BehaviorSubject.createDefault<SensorType>(currentSensorType)
    val sensorState = BehaviorSubject.createDefault<SensorState>(SensorState.NOT_PRESENT)
    val sensorEgv = PublishSubject.create<Int>()

    val availableSensorTypes: List<SensorType> = listOf(
        SensorType.Libre2,
        SensorType.G6,
        SensorType.NONE
    )

    private val dexcomG6ValidPinEntries = listOf(
        "1234",
        "8888",
        "4321"
    )

    companion object {
        private var instance: SensorRepository? = null

        fun getInstance(): SensorRepository = synchronized(this) {
            if (instance == null) {
                instance = SensorRepository()
            }
            instance!!
        }
    }

    fun validateDexcomG6Pin(pin: String): Boolean {
        return dexcomG6ValidPinEntries.contains(pin)
    }

    fun setSensorType(newType: SensorType) {
        if (newType != currentSensorType) {
            currentSensorType = newType
            sensorType.onNext(currentSensorType)
        }
    }

    fun addSensor() {
        cgm = Cgm()
        sensorState.onNext(SensorState.PRESENT)
    }

    fun getSensorEgv() = cgm?.startEgvTransmission()

    fun deleteSensor() {
        cgm?.stopEgvTransmission()
        sensorState.onNext(SensorState.NOT_PRESENT)
        cgm = null
    }

//    fun addSensor() {
//        if (cgm == null) {
//            cgm = Cgm().also { sensor ->
//                egvDisposable = sensor.getEgvObservable()
//                    .subscribeOn(io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        _egv.value = it
////                        sensorEgv.onNext(it)
//                    }
//            }
//            sensorState.onNext(SensorState.PRESENT)
////            _sensorState.postValue(SensorStates.PRESENT)
//        }
//    }

//    fun deleteSensor() {
//        if (cgm != null) {
//            egvDisposable?.dispose()
//            cgm = null
////            _egv.value = null
//            sensorState.onNext(SensorState.NOT_PRESENT)
////            _sensorState.postValue(SensorStates.NOT_PRESENT)
//        }
//    }

    fun setSensorLimit(limit: Int) {
        cgm?.setEgvLimit(limit)
    }

}