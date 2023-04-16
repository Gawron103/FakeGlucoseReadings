package com.example.glucosereadings.presentation.manage_cgm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.domain.use_case.DeleteSensorUseCase
import com.example.glucosereadings.domain.use_case.GetSensorInfoUseCase
import com.example.glucosereadings.domain.use_case.GetSensorStateUseCase
import com.example.glucosereadings.domain.use_case.GetSensorTypeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ManageCgmViewModel(
//    private val getSensorStateUseCase: GetSensorStateUseCase,
//    private val getSensorTypeUseCase: GetSensorTypeUseCase,
    private val getSensorInfoUseCase: GetSensorInfoUseCase,
    private val deleteSensorUseCase: DeleteSensorUseCase
): ViewModel() {

//    private var sensorTypeDisposable: Disposable? = null
//    private var sensorStateDisposable: Disposable? = null
    private var sensorInfoDisposable: Disposable? = null

    private val _uiState = MutableLiveData<ManageCGMUiState>(ManageCGMUiState())
    val uiState: LiveData<ManageCGMUiState> = _uiState

    fun onGetSensorInfo() {
        sensorInfoDisposable = getSensorInfoUseCase()
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                _uiState.postValue(state)
            }
    }

//    fun onGetSensorState() {
//        sensorStateDisposable = getSensorStateUseCase()
//            .observeOn(Schedulers.computation())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe { state ->
//                _uiState.postValue(
//                    uiState.value?.copy(
//                        sensorState = state
//                    )
//                )
//            }
//    }
//
//    fun onGetSensorType() {
//        sensorTypeDisposable = getSensorTypeUseCase()
//            .observeOn(Schedulers.computation())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe { type ->
//                _uiState.postValue(
//                    uiState.value?.copy(
//                        sensorType = type
//                    )
//                )
//            }
//    }

    override fun onCleared() {
//        sensorTypeDisposable?.dispose()
//        sensorStateDisposable?.dispose()
        sensorInfoDisposable?.dispose()
        Log.d("Pika", "ManageCgmVM cleared")
    }

    fun deleteSensor() {
        deleteSensorUseCase()
    }
}