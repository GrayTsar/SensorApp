package com.graytsar.sensorapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

class ModelSensor(
    val sensor: Sensor,
    val title: String,
    val icon: Int,
    val sensorValuesCount: Int,
    val unit:String,
    val backgroundColor:Int,
    private val sensorManager: SensorManager,
    private val navController: NavController )
{
    val xValue = MutableLiveData("default")
    val yValue = MutableLiveData("default")
    val zValue = MutableLiveData("default")

    fun registerSensor():SensorEventListener {
        var o:SensorEventListener? = null

        if(sensorValuesCount == 1){
            o = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                }
                override fun onSensorChanged(event: SensorEvent) {
                    xValue.value = "${event.values[0]} $unit"
                }
            }
        } else if (sensorValuesCount == 3){
            o = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                }
                override fun onSensorChanged(event: SensorEvent) {
                    xValue.value = "${event.values[0]} $unit"
                    yValue.value = "${event.values[1]} $unit"
                    zValue.value = "${event.values[2]} $unit"
                }
            }
        }

        sensorManager.registerListener(o!!, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        return o
    }

    fun onClick(view: View){
        navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", sensor.type) })
    }
}