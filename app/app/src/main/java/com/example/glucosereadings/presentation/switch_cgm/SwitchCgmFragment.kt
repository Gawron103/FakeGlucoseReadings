package com.example.glucosereadings.presentation.switch_cgm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glucosereadings.databinding.FragmentSwitchCgmBinding
import com.example.glucosereadings.domain.use_case.GetAvailableSensorTypesUseCase
import com.example.glucosereadings.domain.use_case.GetSensorTypeUseCase
import com.example.glucosereadings.domain.use_case.SetSensorTypeUseCase

class SwitchCgmFragment : Fragment() {

    private var _binding: FragmentSwitchCgmBinding? = null
    private val binding: FragmentSwitchCgmBinding get() = _binding!!

    private lateinit var sensorsAdapter: SensorsAdapter

    private val viewModel by viewModels<SwitchCgmViewModel> {
        SwitchCgmViewModelFactory(
            SetSensorTypeUseCase(),
            GetAvailableSensorTypesUseCase(),
            GetSensorTypeUseCase()
        )
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSwitchCgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onGetSensorType()

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
        }
    }
}