package com.example.glucosereadings.presentation.add_dexcom_g6

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.databinding.FragmentAddDexcomG6CgmBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding
import com.example.glucosereadings.data.repository.SensorRepository
import com.example.glucosereadings.domain.use_case.AddSensorUseCase
import com.example.glucosereadings.domain.use_case.ValidateG6PinUseCase

class AddDexcomG6CgmFragment : Fragment() {
    private var _binding: FragmentAddDexcomG6CgmBinding? = null
    private val binding: FragmentAddDexcomG6CgmBinding get() = _binding!!

    private val viewModel by viewModels<AddDexcomG6CgmViewModel> {
        AddDexcomG6CgmViewModelFactory(
            AddSensorUseCase(),
            ValidateG6PinUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddDexcomG6CgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPinEntry()

        binding.btnAddG6Cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddG6Add.setOnClickListener {
            if (checkIfAllPinFieldsFilled()) {
                val entry1 = binding.etAddG6PinEntry1.text.toString().trim()
                val entry2 = binding.etAddG6PinEntry2.text.toString().trim()
                val entry3 = binding.etAddG6PinEntry3.text.toString().trim()
                val entry4 = binding.etAddG6PinEntry4.text.toString().trim()
                val pin = "$entry1$entry2$entry3$entry4"

                val res = viewModel.addSensor(pin)
                if (res) {
                    showSensorAddedAlert()
                }
            }
        }
    }

    private fun setupPinEntry() {
        binding.etAddG6PinEntry1.doOnTextChanged { text, _, _, _ ->
            if (text.toString().trim().isNotEmpty()) {
                binding.etAddG6PinEntry2.requestFocus()
            }
        }
        binding.etAddG6PinEntry2.doOnTextChanged { text, _, _, _ ->
            if (text.toString().trim().isNotEmpty()) {
                binding.etAddG6PinEntry3.requestFocus()
            }
        }
        binding.etAddG6PinEntry3.doOnTextChanged { text, _, _, _ ->
            if (text.toString().trim().isNotEmpty()) {
                binding.etAddG6PinEntry4.requestFocus()
            }
        }
        binding.etAddG6PinEntry4.doOnTextChanged { text, _, _, _ ->
            if (text.toString().trim().isNotEmpty()) {
                binding.etAddG6PinEntry4.clearFocus()
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun checkIfAllPinFieldsFilled(): Boolean {
        if (binding.etAddG6PinEntry1.text.isEmpty()) {
            return false
        }
        if (binding.etAddG6PinEntry2.text.isEmpty()) {
            return false
        }
        if (binding.etAddG6PinEntry3.text.isEmpty()) {
            return false
        }
        if (binding.etAddG6PinEntry4.text.isEmpty()) {
            return false
        }

        return true
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