package com.graytsar.sensorapp

import android.graphics.Bitmap
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ModelDetail(val sensor: Sensor,
                  val title: String,
                  val icon: Bitmap,
                  private val displayPoints: Float,
                  val sensorValuesCount: Int,
                  private val sensorManager: SensorManager,
                  val name: String,
                  val vendor: String,
                  val version: String,
                  val power: String,
                  val maxDelay: String,
                  val minDelay: String,
                  val maxRange: String,
                  val unit: String,
                  val information: String )
{
    val xValue = MutableLiveData<String>("default")
    val yValue = MutableLiveData<String>("default")
    val zValue = MutableLiveData<String>("default")

    var csvHeader:String = ""

    init {
        csvHeader = "TIMESTAMP,X,Y,Z,${sensor.name},VENDOR:${sensor.vendor},VERSION:${sensor.version},POWER:${sensor.power},MAXDELAY:${sensor.maxDelay},MINDELAY:${sensor.minDelay},MAXRANGE:${sensor.maximumRange}"
    }

    private var mData:LineData = LineData()
    var mChart:LineChart? = null
        set(value) {
            field = value
            setupChart(sensorValuesCount, mData, value!!)
        }

    fun registerSensor(): SensorEventListener {
        var o: SensorEventListener? = null

        if(sensorValuesCount == 1){
            var i = 0

            o = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                }
                override fun onSensorChanged(event: SensorEvent) {
                    xValue.value = "${event.values[0]} $unit"

                    if(mChart != null){
                        addPointsToChart(i, event.values)
                        i++
                    }
                }
            }
        } else if (sensorValuesCount == 3){
            o = object : SensorEventListener {
                var i = 0

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                }
                override fun onSensorChanged(event: SensorEvent) {
                    xValue.value = "${event.values[0]} $unit"
                    yValue.value = "${event.values[1]} $unit"
                    zValue.value = "${event.values[2]} $unit"

                    if(mChart != null){
                        addPointsToChart(i, event.values)
                        i++
                    }
                }
            }
        }

        sensorManager.registerListener(o!!, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        return o
    }

    fun addPointsToChart(position:Int, valueAr:FloatArray){
        var i = 0 //axis index

        for(c in 0 until sensorValuesCount) {
            val entry = Entry(position.toFloat(), valueAr[i])
            mData.addEntry(entry, i)
            if (mData.getDataSetByIndex(i).entryCount > displayPoints)
                mData.getDataSetByIndex(i).removeFirst()
            i++
        }

        mData.notifyDataChanged() //tell LineData to do its magic
        mChart?.notifyDataSetChanged() //tell LineChart to do its magic
        mChart?.setVisibleXRange(displayPoints, displayPoints)
        mChart?.moveViewToX(position.toFloat()) //auto calls invalidate

    }

    private fun setupChart(countGraphs:Int, mData: LineData, mChart: LineChart){
        val colorAr:IntArray = intArrayOf(Color.BLUE, Color.RED, Color.GREEN)

        mChart.description.isEnabled = false //disable description at the right bottom corner
        mChart.legend.textSize = 16f
        mChart.legend.textColor = Color.GRAY
        mChart.axisLeft.isEnabled = false
        mChart.axisLeft.setDrawGridLines(false) //disable background grid lines
        mChart.axisRight.textSize = 16f
        mChart.axisRight.setDrawGridLines(false) //disable background grid lines
        mChart.axisRight.textColor = Color.GRAY
        mChart.xAxis.setDrawGridLines(false) //disable background grid lines
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM //x axis position
        mChart.xAxis.setDrawLabels(false) //disable draw axis labels
        mChart.setTouchEnabled(false) //disable all touch related stuff
        mChart.setHardwareAccelerationEnabled(true)

        for(i in 0 until  countGraphs)
        {
            mData.addDataSet(
                createSet(
                    ('x'.toInt() + i).toChar() + " Axis",
                    colorAr[i]
                )
            )
        }
        mChart.data = mData
    }

    private fun createSet(description:String, _color:Int): LineDataSet {
        val set = LineDataSet(null, description)
        set.setDrawCircles(false)//disable circles around points
        set.color = _color
        set.lineWidth = 2f
        set.setDrawValues(false) //disabled point labels
        return set
    }
}