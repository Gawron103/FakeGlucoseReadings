package com.example.glucosereadings.presentation.manage_cgm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.domain.use_case.DeleteSensorUseCase
import com.example.glucosereadings.domain.use_case.GetSensorInfoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ManageCgmViewModel(
    private val getSensorInfoUseCase: GetSensorInfoUseCase,
    private val deleteSensorUseCase: DeleteSensorUseCase
): ViewModel() {

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

    override fun onCleared() {
        sensorInfoDisposable?.dispose()
    }

    fun deleteSensor() {
        deleteSensorUseCase()
    }
}