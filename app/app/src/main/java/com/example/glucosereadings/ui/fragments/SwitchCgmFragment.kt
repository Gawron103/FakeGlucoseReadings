package com.example.glucosereadings.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glucosereadings.R
import com.example.glucosereadings.databinding.FragmentSwitchCgmBinding
import com.example.glucosereadings.models.SensorType
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.ui.adapters.SensorsAdapter
import com.example.glucosereadings.viewmodels.SwitchCgmViewModel
import com.example.glucosereadings.viewmodels.SwitchCgmViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SwitchCgmFragment : Fragment() {

    private var _binding: FragmentSwitchCgmBinding? = null
    private val binding: FragmentSwitchCgmBinding get() = _binding!!

    private lateinit var sensorsAdapter: SensorsAdapter

    private val viewModel by viewModels<SwitchCgmViewModel> {
        SwitchCgmViewModelFactory(SensorRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSwitchCgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sensorsAdapter = SensorsAdapter(viewModel.getAvailableSensors()) { selected ->
            viewModel.checkIfSwitchCgmShouldBeEnabled(selected)
        }

        viewModel.currentSensorTypeLiveData.observe(viewLifecycleOwner) { currentType ->
            sensorsAdapter.setCurrentSensor(currentType)
        }

        viewModel.enableSwitchCgmOptionLiveData.observe(viewLifecycleOwner) { shouldEnable ->
            binding.btnSwitchCgmSwitch.isEnabled = shouldEnable
        }

        binding.rvSwitchCgmSensors.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sensorsAdapter
        }

        binding.btnSwitchCGMCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSwitchCgmSwitch.setOnClickListener {
            viewModel.setSensorType(sensorsAdapter.getSelectedSensor())
            findNavController().popBackStack()
//            Toast.makeText(requireContext(), "Selected sensor: ${sensorsAdapter.getSelectedSensor()}", Toast.LENGTH_SHORT).show()
        }
    }
}