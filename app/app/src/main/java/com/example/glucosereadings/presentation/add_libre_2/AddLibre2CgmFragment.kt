package com.example.glucosereadings.presentation.add_libre_2

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.databinding.FragmentAddLibre2CgmBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding
import com.example.glucosereadings.domain.use_case.AddSensorUseCase

class AddLibre2CgmFragment : Fragment() {

    private var _binding: FragmentAddLibre2CgmBinding? = null
    private val binding: FragmentAddLibre2CgmBinding get() = _binding!!

    private val viewModel by viewModels<AddLibre2CgmViewModel> {
        AddLibre2CgmViewModelFactory(
            AddSensorUseCase()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLibre2CgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivAddCgmCgmIcon.setOnLongClickListener {
            viewModel.addSensor()
            showSensorAddedAlert()
            true
        }
    }

    private fun showSensorAddedAlert() {
        val dialogView = SensorAlertDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).create()

        with(dialogView) {
            tvSensorAlertDialogTitle.text = "Add Sensor"
            tvSensorAlertDialogMessage.text = "Sensor has been added"
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