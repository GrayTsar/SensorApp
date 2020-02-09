package com.graytsar.sensorapp


import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


object SensorSingleton: ViewModel() {
    private const val showCountPoints = 300f

    fun registerSelected(sensorManager:SensorManager, mSensor:Sensor, action:(arr:SensorEvent) -> Unit ):SensorEventListener{
        val o = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            }
            override fun onSensorChanged(event: SensorEvent) {
                action(event)
            }
        }

        sensorManager.registerListener( o, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
        return o
    }

    fun setupChart(countGraphs:Int, mData: LineData, mChart: LineChart){
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
            mData.addDataSet(createSet(('x'.toInt() + i).toChar() + " Axis", colorAr[i]))
        }
        mChart.data = mData
    }

    fun addPointsToChart(position:Int, mData: LineData, mChart: LineChart, valueAr:FloatArray, safety:Boolean = false){
        var i = 0 //axis index

        if(!safety){
            for(c in 0 until 3) { //xiaomi mi a3 uses uncalibrated version of default magnet sensor -> 5 values for uncalibrated
                val entry = Entry(position.toFloat(), valueAr[i])
                mData.addEntry(entry, i)
                if (mData.getDataSetByIndex(i).entryCount > showCountPoints)
                    mData.getDataSetByIndex(i).removeFirst()
                i++
            }
        }
        else { //xiaomi mi a3 has light/pressure sensor valueAr.size == 3 | small hack -> 1 value sensor set safety = true
            val entry = Entry(position.toFloat(), valueAr[0])
            mData.addEntry(entry, 0)
            if (mData.getDataSetByIndex(0).entryCount > showCountPoints)
                mData.getDataSetByIndex(0).removeFirst()
        }

        mData.notifyDataChanged() //tell LineData to do its magic
        mChart.notifyDataSetChanged() //tell LineChart to do its magic
        mChart.setVisibleXRange(showCountPoints, showCountPoints)
        mChart.moveViewToX(position.toFloat()) //auto calls invalidate

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

