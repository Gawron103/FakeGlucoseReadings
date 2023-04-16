package com.example.glucosereadings.presentation.add_dexcom_g6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.AddSensorUseCase
import com.example.glucosereadings.domain.use_case.ValidateG6PinUseCase

class AddDexcomG6CgmViewModelFactory constructor(
    private val addSensorUseCase: AddSensorUseCase,
    private val validateG6PinUseCase: ValidateG6PinUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddDexcomG6CgmViewModel::class.java) -> {
                AddDexcomG6CgmViewModel(
                    addSensorUseCase,
                    validateG6PinUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}