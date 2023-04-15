package com.example.glucosereadings.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glucosereadings.databinding.ItemSensorBinding
import com.example.glucosereadings.models.SensorType

class SensorsAdapter(
    private val sensorsList: List<SensorType>,
    private val onSensorChecked: (type: SensorType) -> Unit
): RecyclerView.Adapter<SensorsAdapter.SensorViewHolder>() {

    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val binding = ItemSensorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        holder.bind(sensorsList[position], position, selectedItem)
    }

    override fun getItemCount(): Int {
        return sensorsList.size
    }

    fun getSelectedSensor(): SensorType {
        return if (selectedItem != -1) {
            sensorsList[selectedItem]
        } else {
            SensorType.NONE
        }
    }

    fun setCurrentSensor(type: SensorType) {
        val index = sensorsList.indexOf(type)
        if (index != -1) {
            selectedItem = index
            onSensorChecked(sensorsList[selectedItem])
            notifyDataSetChanged()
        } else {
            selectedItem = -1
        }
    }

    inner class SensorViewHolder(private val binding: ItemSensorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(type: SensorType, position: Int, selectedPosition: Int) {
            binding.rbSensor.text = type.toString()

            if (selectedPosition == position) {
                binding.rbSensor.isChecked = true
            } else {
                binding.rbSensor.isChecked = false
                binding.rbSensor.setOnClickListener {
                    selectedItem = adapterPosition
                    onSensorChecked(sensorsList[selectedItem])
                    notifyDataSetChanged()
                }
            }
        }
    }

}