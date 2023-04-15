package com.example.glucosereadings.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.R
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import com.example.glucosereadings.viewmodels.SensorManagementViewModelFactory
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.databinding.FragmentManageCgmBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding
import com.example.glucosereadings.models.SensorType
import com.example.glucosereadings.utils.SensorState

class ManageCgmFragment : Fragment() {

    private var _binding: FragmentManageCgmBinding? = null
    private val binding: FragmentManageCgmBinding get() = _binding!!

    private val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
        SensorManagementViewModelFactory(SensorRepository.getInstance())
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
        observeSensorType()
        observeSensorState()

        binding.btnManageCgmSwitchSensor.setOnClickListener {
            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToSwitchCgmFragment())
        }
    }

    private fun observeSensorState() {
        sensorManagementViewModel.sensorStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                SensorState.NOT_PRESENT -> {
                    setViewToSensorNotPresented()
                }
                SensorState.PRESENT -> {
                    setViewToSensorPresented()
                }
            }
        }
    }

    private fun observeSensorType() {
        sensorManagementViewModel.sensorTypeLiveData.observe(viewLifecycleOwner) { type ->
            when (type) {
                SensorType.NONE -> {
                    binding.tvManageCgmSensorType.text = "Sensor type: $type"
                    binding.btnManageCgmAddCgm.isEnabled = false
                }
                else -> {
                    binding.tvManageCgmSensorType.text = "Sensor type: $type"
                    binding.btnManageCgmAddCgm.isEnabled = true
                    binding.btnManageCgmAddCgm.setOnClickListener {
                        if (type == SensorType.G6) {
                            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddDexcomG6CgmFragment())
                        } else {
                            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddLibre2CgmFragment())
                        }
                    }
                }
            }
        }
    }

    private fun setViewToSensorPresented() {
        binding.tvManageCgmSensorStateInfo.text = "SENSOR PRESENTED"
        binding.btnManageCgmAddCgm.setImageResource(R.drawable.ic_remove)
        binding.btnManageCgmAddCgm.setOnClickListener {
            sensorManagementViewModel.deleteSensor()
            showSensorDeleteAlert()
        }
    }

    private fun setViewToSensorNotPresented() {
        binding.tvManageCgmSensorStateInfo.text = "NO SENSOR"
        binding.btnManageCgmAddCgm.setImageResource(R.drawable.ic_add)
//        binding.btnManageCgmAddCgm.setOnClickListener {
//
//            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddCgmFragment())
//        }
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