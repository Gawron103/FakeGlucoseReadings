package com.example.glucosereadings.presentation.add_libre_2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.domain.use_case.AddSensorUseCase

class AddLibre2CgmViewModelFactory constructor(
  private val addSensorUseCase: AddSensorUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddLibre2CgmViewModel::class.java) -> {
                AddLibre2CgmViewModel(addSensorUseCase) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }

}