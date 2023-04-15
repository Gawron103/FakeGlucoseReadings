package com.example.glucosereadings.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.models.SensorType
import com.example.glucosereadings.repositories.SensorRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SwitchCgmViewModel(private val repository: SensorRepository): ViewModel() {

    private var disposable: Disposable? = null

    private val _currentSensorTypeMutableLiveData = MutableLiveData<SensorType>()
    val currentSensorTypeLiveData: LiveData<SensorType> = _currentSensorTypeMutableLiveData

    private val _enableSwitchCgmOptionMutableLiveData = MutableLiveData<Boolean>()
    val enableSwitchCgmOptionLiveData: LiveData<Boolean> = _enableSwitchCgmOptionMutableLiveData

    init {
        disposable = repository.sensorType
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { type ->
                _currentSensorTypeMutableLiveData.postValue(type)
            }
    }

    override fun onCleared() {
        disposable?.dispose()
    }

    fun checkIfSwitchCgmShouldBeEnabled(selectedSensor: SensorType) {
        _enableSwitchCgmOptionMutableLiveData.value = currentSensorTypeLiveData.value != selectedSensor
    }

    fun getAvailableSensors() = repository.availableSensorTypes

    fun setSensorType(newType: SensorType): Boolean {
        return if (currentSensorTypeLiveData.value != newType) {
            repository.setSensorType(newType)
            true
        } else {
            false
        }
    }

}