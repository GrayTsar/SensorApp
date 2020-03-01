package com.graytsar.sensorapp

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graytsar.sensorapp.databinding.ItemSensorBinding
import kotlinx.android.synthetic.main.item_sensor.view.*

class AdapterSensor(private val activity: FragmentActivity):ListAdapter<ModelSensor, ViewHolderSensor>(DiffCallbackItemHome()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSensor {
        val binding = DataBindingUtil.inflate<ItemSensorBinding>(LayoutInflater.from(activity), R.layout.item_sensor, parent, false)
        return ViewHolderSensor(binding.root, binding)
    }

    override fun onBindViewHolder(holderSensor: ViewHolderSensor, position: Int) {
        holderSensor.binding.lifecycleOwner = activity
        holderSensor.binding.modelSensor = getItem(position)

        holderSensor.icon.setImageBitmap(getItem(position).icon)

        if(getItem(position).sensorValuesCount == 1){
            holderSensor.tView2.visibility = View.GONE
            holderSensor.tView3.visibility = View.GONE
        } else {
            holderSensor.tView2.visibility = View.VISIBLE
            holderSensor.tView3.visibility = View.VISIBLE
        }

        when(getItem(position).sensor.type){
            Sensor.TYPE_ACCELEROMETER -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorRed))
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPink))
            }
            Sensor.TYPE_GRAVITY -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPurple))
            }
            Sensor.TYPE_GYROSCOPE -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorDeepPurple))
            }
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorIndigo))
            }
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorBlue))
            }
            Sensor.TYPE_LIGHT -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLightBlue))
            }
            Sensor.TYPE_PRESSURE -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorCyan))
            }
            Sensor.TYPE_RELATIVE_HUMIDITY -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorTeal))
            }
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorGreen))
            }
            Sensor.TYPE_PROXIMITY -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLightGreen))
            }
            Sensor.TYPE_STEP_COUNTER -> {
                holderSensor.backgroundColor.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLime))
            }
        }
    }
}

class ViewHolderSensor(view: View, val binding:ItemSensorBinding):RecyclerView.ViewHolder(view){
    val icon: ImageView = view.imageViewSensor
    val backgroundColor:View = view.backgroundColorSensor
    val tView2: TextView = view.textSensorVal2
    val tView3: TextView = view.textSensorVal3
}

class DiffCallbackItemHome: DiffUtil.ItemCallback<ModelSensor>(){
    override fun areItemsTheSame(oldItem: ModelSensor, newItem: ModelSensor): Boolean {
        return oldItem.sensor.type == newItem.sensor.type
    }

    override fun areContentsTheSame(oldItem: ModelSensor, newItem: ModelSensor): Boolean {
        return false
    }

}