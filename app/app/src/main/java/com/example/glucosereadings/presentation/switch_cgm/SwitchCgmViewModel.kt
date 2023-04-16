package com.example.glucosereadings.presentation.switch_cgm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.data.model.SensorType
import com.example.glucosereadings.data.repository.SensorRepository
import com.example.glucosereadings.domain.use_case.GetAvailableSensorTypesUseCase
import com.example.glucosereadings.domain.use_case.GetSensorTypeUseCase
import com.example.glucosereadings.domain.use_case.SetSensorTypeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SwitchCgmViewModel(
    private val setSensorTypeUseCase: SetSensorTypeUseCase,
    private val getAvailableSensorTypesUseCase: GetAvailableSensorTypesUseCase,
    private val getSensorTypeUseCase: GetSensorTypeUseCase
    ): ViewModel() {

    private var disposable: Disposable? = null

    private val _currentSensorTypeMutableLiveData = MutableLiveData<SensorType>()
    val currentSensorTypeLiveData: LiveData<SensorType> = _currentSensorTypeMutableLiveData

    private val _enableSwitchCgmOptionMutableLiveData = MutableLiveData<Boolean>()
    val enableSwitchCgmOptionLiveData: LiveData<Boolean> = _enableSwitchCgmOptionMutableLiveData

    override fun onCleared() {
        disposable?.dispose()
    }

    fun onGetSensorType() {
        disposable = getSensorTypeUseCase()
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { type ->
                _currentSensorTypeMutableLiveData.postValue(type)
            }
    }

    fun checkIfSwitchCgmShouldBeEnabled(selectedSensor: SensorType) {
        _enableSwitchCgmOptionMutableLiveData.value = currentSensorTypeLiveData.value != selectedSensor
    }

    fun getAvailableSensors() = getAvailableSensorTypesUseCase()

    fun setSensorType(newType: SensorType): Boolean {
        return if (currentSensorTypeLiveData.value != newType) {
            setSensorTypeUseCase(newType)
            true
        } else {
            false
        }
    }

}