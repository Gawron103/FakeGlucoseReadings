package com.example.glucosereadings.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.glucosereadings.databinding.FragmentHomeBinding
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.SensorState
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import com.example.glucosereadings.viewmodels.SensorManagementViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
        SensorManagementViewModelFactory(SensorRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeCgmState()
        observeEgvValue()
    }

    private fun observeCgmState() {
        sensorManagementViewModel.sensorStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                SensorState.NOT_PRESENT -> { showNoSensor() }
                SensorState.PRESENT -> { showSensorPresented() }
            }
        }
    }

    private fun observeEgvValue() {
        sensorManagementViewModel.egv.observe(viewLifecycleOwner) { egv ->
            when (egv) {
                null -> { binding.tvHomeEgvValue.text = "---" }
                else -> { binding.tvHomeEgvValue.text = egv.toString() }
            }
        }
    }

    private fun showNoSensor() {
        binding.tvHomeSensorStatusValue.text = "NO SENSOR"
        binding.tvHomeEgvHint.visibility = View.VISIBLE
    }

    private fun showSensorPresented() {
        binding.tvHomeSensorStatusValue.text = "SENSOR PRESENTED"
        binding.tvHomeEgvHint.visibility = View.GONE
    }

}