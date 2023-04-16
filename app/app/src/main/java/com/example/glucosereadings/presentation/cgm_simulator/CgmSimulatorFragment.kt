package com.example.glucosereadings.presentation.cgm_simulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import com.example.glucosereadings.databinding.FragmentCgmSimulatorBinding
import com.example.glucosereadings.domain.use_case.*
import com.example.glucosereadings.data.model.SensorState

class CgmSimulatorFragment : Fragment() {

    private var _binding: FragmentCgmSimulatorBinding? = null
    private val binding: FragmentCgmSimulatorBinding get() = _binding!!

//    private val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
//        SensorManagementViewModelFactory(
//            GetSensorStateUseCase(),
//            GetSensorTypeUseCase(),
//            GetSensorEgvUseCase(),
//            AddSensorUseCase(),
//            DeleteSensorUseCase()
//        )
//    }

    private val viewModel by viewModels<CgmSimulatorViewModel> {
        CgmSimulatorViewModelFactory(
            SetSensorEgvLimitUseCase(),
            GetSensorStateUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onGetSensorState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCgmSimulatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeSensorState()
        setupSeekBar()
        setupApplyButton()
    }

    private fun observeSensorState() {
        viewModel.sensorStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                SensorState.NOT_PRESENT -> { hideSimulatorShowInfo() }
                SensorState.PRESENT -> { showSimulatorHideInfo() }
            }
        }
    }

    private fun setupSeekBar() {
        binding.sbSimulator.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvSimulatorEgvValue.text = seek?.progress?.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                /* do nothing */
            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                /* do nothing */
            }
        })
    }

    private fun setupApplyButton() {
        binding.btnSimulatorApply.setOnClickListener {
            if (binding.tvSimulatorEgvValue.text != "---") {
                val newLimit = binding.tvSimulatorEgvValue.text.toString().toInt()
                viewModel.setEgvLimit(newLimit)
            }
        }
    }

    private fun hideSimulatorShowInfo() {
        binding.btnSimulatorApply.visibility = View.GONE
        binding.tvSimulatorEgvValue.visibility = View.GONE
        binding.tvSimulatorEgvLimitLabel.visibility = View.GONE
        binding.sbSimulator.visibility = View.GONE

        binding.tvSimulatorNoSensorLabel.visibility = View.VISIBLE
    }

    private fun showSimulatorHideInfo() {
        binding.btnSimulatorApply.visibility = View.VISIBLE
        binding.tvSimulatorEgvValue.visibility = View.VISIBLE
        binding.tvSimulatorEgvLimitLabel.visibility = View.VISIBLE
        binding.sbSimulator.visibility = View.VISIBLE

        binding.tvSimulatorNoSensorLabel.visibility = View.GONE
    }
}