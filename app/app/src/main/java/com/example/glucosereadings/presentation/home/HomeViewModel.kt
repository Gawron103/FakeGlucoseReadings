package com.example.glucosereadings.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.domain.use_case.GetSensorEgvUseCase
import com.example.glucosereadings.domain.use_case.GetSensorStateUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val getSensorEgvUseCase: GetSensorEgvUseCase
): ViewModel() {

    private var disposable: Disposable? = null

    private val _sensorEgvMutableLiveData = MutableLiveData<Int?>(null)
    val sensorEgvLiveData: LiveData<Int?> = _sensorEgvMutableLiveData


    fun getSensorEgv() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
        disposable = getSensorEgvUseCase()
            ?.observeOn(Schedulers.computation())
            ?.subscribeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { egv ->
                    _sensorEgvMutableLiveData.postValue(egv)
                },
                {

                },
                {
                    _sensorEgvMutableLiveData.postValue(null)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}