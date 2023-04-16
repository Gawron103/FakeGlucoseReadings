package com.example.glucosereadings.presentation.manage_cgm

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.R
import com.example.glucosereadings.databinding.FragmentManageCgmBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding
import com.example.glucosereadings.domain.use_case.DeleteSensorUseCase
import com.example.glucosereadings.domain.use_case.GetSensorStateUseCase
import com.example.glucosereadings.domain.use_case.GetSensorTypeUseCase
import com.example.glucosereadings.data.model.SensorType
import com.example.glucosereadings.data.model.SensorState
import com.example.glucosereadings.domain.use_case.GetSensorInfoUseCase

class ManageCgmFragment : Fragment() {

    private var _binding: FragmentManageCgmBinding? = null
    private val binding: FragmentManageCgmBinding get() = _binding!!

    private val viewModel by viewModels<ManageCgmViewModel> {
        ManageCgmViewModelFactory(
            GetSensorInfoUseCase(GetSensorStateUseCase(), GetSensorTypeUseCase()),
            DeleteSensorUseCase()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onGetSensorInfo()

        observeUiState()

        binding.btnManageCgmSwitchSensor.setOnClickListener {
            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToSwitchCgmFragment())
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.tvManageCgmSensorType.text = "Sensor type: ${state.sensorType}"

            if (state.sensorState == SensorState.NOT_PRESENT) {
                binding.tvManageCgmSensorStateInfo.text = "NO SENSOR"
                binding.btnManageCgmAddCgm.setImageResource(R.drawable.ic_add)

                when (state.sensorType) {
                    SensorType.NONE -> {
                        binding.btnManageCgmAddCgm.isEnabled = false
                    }
                    SensorType.Libre2 -> {
                        binding.btnManageCgmAddCgm.isEnabled = true
                        binding.btnManageCgmAddCgm.setOnClickListener {
                            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddLibre2CgmFragment())
                        }
                    }
                    SensorType.G6 -> {
                        binding.btnManageCgmAddCgm.isEnabled = true
                        binding.btnManageCgmAddCgm.setOnClickListener {
                            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddDexcomG6CgmFragment())
                        }
                    }
                }
            } else {
                binding.tvManageCgmSensorStateInfo.text = "SENSOR PRESENTED"
                binding.btnManageCgmAddCgm.setImageResource(R.drawable.ic_remove)
                binding.btnManageCgmAddCgm.isEnabled = true
                binding.btnManageCgmAddCgm.setOnClickListener {
                    viewModel.deleteSensor()
                    showSensorDeleteAlert()
                }
            }
        }
    }

    private fun showSensorDeleteAlert() {
        val dialogView = SensorAlertDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).create()

        with(dialogView) {
            tvSensorAlertDialogTitle.text = "Sensor Management"
            tvSensorAlertDialogMessage.text = "Sensor has been deleted"
            btnSensorAlertDialogPositive.setOnClickListener {
                dialog.dismiss()
                findNavController().popBackStack()
            }
        }

        with(dialog) {
            setCancelable(false)
            setView(dialogView.root)
            show()
        }
    }

}