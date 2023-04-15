package com.example.glucosereadings.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.models.SensorType
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.SensorState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SensorManagementViewModel(private val repository: SensorRepository): ViewModel() {

    private var sensorTypeDisposable: Disposable? = null
    private var sensorStateDisposable: Disposable? = null

//    val sensorState = repository.sensorState
    val egv = repository.egv

    private val _sensorTypeMutableLiveData = MutableLiveData<SensorType>()
    val sensorTypeLiveData: LiveData<SensorType> = _sensorTypeMutableLiveData

    private val _sensorStateMutableLiveData = MutableLiveData<SensorState>()
    val sensorStateLiveData: LiveData<SensorState> = _sensorStateMutableLiveData

    init {
        sensorTypeDisposable = repository.sensorType
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { type ->
                _sensorTypeMutableLiveData.postValue(type)
            }

        sensorStateDisposable = repository.sensorState
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                _sensorStateMutableLiveData.postValue(state)
            }
    }

    override fun onCleared() {
        sensorTypeDisposable?.dispose()
        sensorStateDisposable?.dispose()
    }

    fun addSensor() {
        repository.addSensor()
    }

    fun deleteSensor() {
        repository.deleteSensor()
    }

    fun setSensorLimit(limit: Int) {
        repository.setSensorLimit(limit)
    }

}