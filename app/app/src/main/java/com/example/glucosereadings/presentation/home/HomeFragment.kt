package com.example.glucosereadings.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.glucosereadings.databinding.FragmentHomeBinding
import com.example.glucosereadings.domain.use_case.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(GetSensorEgvUseCase())
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
        viewModel.getSensorEgv()
        observeEgvValue()
    }

    private fun observeEgvValue() {
        viewModel.sensorEgvLiveData.observe(viewLifecycleOwner) { egv ->
            when (egv) {
                null -> {
                    binding.tvHomeEgvValue.text = "---"
                    binding.tvHomeEgvHint.visibility = View.VISIBLE
                }
                else -> {
                    binding.tvHomeEgvValue.text = egv.toString()
                    binding.tvHomeEgvHint.visibility = View.GONE
                }
            }
        }
    }

}