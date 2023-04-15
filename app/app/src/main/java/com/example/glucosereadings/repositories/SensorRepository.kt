package com.example.glucosereadings.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.models.Cgm
import com.example.glucosereadings.models.SensorType
import com.example.glucosereadings.utils.SensorState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import io.reactivex.subjects.BehaviorSubject

class SensorRepository private constructor() {

    private var cgm: Cgm? = null
    private var egvDisposable: Disposable? = null

    private val _egv = MutableLiveData<Int?>(null)
    val egv: LiveData<Int?> get() = _egv

    private var currentSensorType = SensorType.NONE

    val sensorType = BehaviorSubject.createDefault<SensorType>(currentSensorType)
    val sensorState = BehaviorSubject.createDefault<SensorState>(SensorState.NOT_PRESENT)
//    val sensorEgv = BehaviorSubject.createDefault<Int?>(null)

//    private val _sensorState = MutableLiveData(SensorStates.NOT_PRESENT)
//    val sensorState: LiveData<SensorStates> get() = _sensorState

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
        if (cgm == null) {
            cgm = Cgm().also { sensor ->
                egvDisposable = sensor.getEgvObservable()
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _egv.value = it
//                        sensorEgv.onNext(it)
                    }
            }
            sensorState.onNext(SensorState.PRESENT)
//            _sensorState.postValue(SensorStates.PRESENT)
        }
    }

    fun deleteSensor() {
        if (cgm != null) {
            egvDisposable?.dispose()
            cgm = null
//            _egv.value = null
            sensorState.onNext(SensorState.NOT_PRESENT)
//            _sensorState.postValue(SensorStates.NOT_PRESENT)
        }
    }

    fun setSensorLimit(limit: Int) {
        cgm?.setEgvLimit(limit)
    }

}