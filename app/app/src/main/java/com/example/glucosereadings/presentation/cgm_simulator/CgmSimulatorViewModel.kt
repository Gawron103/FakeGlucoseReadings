package com.example.glucosereadings.presentation.cgm_simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.domain.use_case.GetSensorStateUseCase
import com.example.glucosereadings.domain.use_case.SetSensorEgvLimitUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CgmSimulatorViewModel(
    private val setSensorEgvLimitUseCase: SetSensorEgvLimitUseCase,
    private val getSensorStateUseCase: GetSensorStateUseCase
): ViewModel() {

    private var sensorStateDisposable: Disposable? = null

    private val _sensorStateMutableLiveData = MutableLiveData<SensorState>()
    val sensorStateLiveData: LiveData<SensorState> = _sensorStateMutableLiveData

    fun onGetSensorState() {
        sensorStateDisposable = getSensorStateUseCase()
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                _sensorStateMutableLiveData.postValue(state)
            }
    }

    fun setEgvLimit(limit: Int) {
        setSensorEgvLimitUseCase(limit)
    }

    override fun onCleared() {
        super.onCleared()
        sensorStateDisposable?.dispose()
    }

}