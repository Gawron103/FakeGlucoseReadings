package com.example.glucosereadings.domain.use_case

import com.example.glucosereadings.presentation.manage_cgm.ManageCGMUiState
import io.reactivex.Observable

class GetSensorInfoUseCase(
    private val getSensorStateUseCase: GetSensorStateUseCase,
    private val getSensorTypeUseCase: GetSensorTypeUseCase
) {

    operator fun invoke(): Observable<ManageCGMUiState> =
        Observable.zip(
            getSensorStateUseCase(),
            getSensorTypeUseCase(),
        ) { state, type ->
            ManageCGMUiState(state, type)
        }

}