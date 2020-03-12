package com.graytsar.sensorapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat
import java.io.*

class ForegroundServiceWriteFile: Service() {
    private var notificationManager:NotificationManager? = null
    private var channelID:String = "com.graytsar.sensorapp.Log"
    private val notificationID:Int = 101

    private var sensorEventListener:SensorEventListener? = null
    private var sensorManager:SensorManager? = null

    private var writer:FileOutputStream? = null

    //1 KB safety because of 4GB hard file size limit
    val gbInByte = 3999999000

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val enableLog:Boolean = intent!!.getBooleanExtra("enableLog", false)

        if(enableLog){
            val sensorType = intent.getIntExtra("sensorType", 1)
            val title = intent.getStringExtra("title")
            val sensorValuesCount = intent.getIntExtra("sensorValuesCount", 1)
            val csvHeader = intent.getStringExtra("csvHeader")
            val fUri = Uri.parse(intent.getStringExtra("fUri"))

            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager!!.getDefaultSensor(sensorType)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(channelID, "Log", "Log Sensor Data")

            val notification = NotificationCompat.Builder(this, channelID).apply {
                setContentTitle("${getString(R.string.logging)} $title")
                setContentText(getString(R.string.maxFileSize))
                setSmallIcon(R.drawable.baseline_refresh_black_24dp)
                setChannelId(channelID)
            }

            notification.setProgress(1000000, 0, false)

            if(sensorValuesCount == 1){
                val pfd: ParcelFileDescriptor = applicationContext.contentResolver.openFileDescriptor(fUri, "w")!!
                val bW = FileOutputStream(pfd.fileDescriptor)
                bW.write(csvHeader!!.toByteArray())
                writer = bW

                sensorEventListener = object : SensorEventListener {
                    val d = pfd.dup()
                    val dupWriter = FileOutputStream(d.fileDescriptor)

                    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

                    }
                    override fun onSensorChanged(event: SensorEvent) {
                        if(gbInByte > d.statSize){
                            dupWriter.write("\n${event.timestamp},${event.values[0]},0,0,".toByteArray())
                            bW.flush()

                            notification.setProgress(100, ((d.statSize / gbInByte.toDouble()) * 100).toInt(), false)
                            notificationManager!!.notify(1, notification.build())
                        } else {
                            dupWriter.close()
                        }
                    }
                }
            } else if (sensorValuesCount == 3){
                val pfd: ParcelFileDescriptor = applicationContext.contentResolver.openFileDescriptor(fUri, "w")!!
                val bW = FileOutputStream(pfd.fileDescriptor)
                bW.write(csvHeader!!.toByteArray())
                writer = bW

                sensorEventListener = object : SensorEventListener {
                    val d = pfd.dup()
                    val dupWriter = FileOutputStream(d.fileDescriptor)

                    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                    }
                    override fun onSensorChanged(event: SensorEvent) {
                        if(gbInByte > d.statSize){

                            //for(i in 0..500){
                                dupWriter.write("\n${event.timestamp},${event.values[0]},${event.values[1]},${event.values[2]}".toByteArray())
                                dupWriter.flush()
                            //}

                            notification.setProgress(100, ((d.statSize / gbInByte.toDouble()) * 100).toInt(), false)
                            notificationManager!!.notify(1, notification.build())
                            //Log.d("DBG: ", " " + (d.statSize / gbInByte.toDouble()) * 100.toDouble())
                        }
                        else {
                            dupWriter.close()
                            //Log.d("DBG: ", "CLOSED")
                        }
                    }
                }
            }
            sensorManager!!.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST)

            //id can not be 0
            startForeground(1, notification.build())
        } else {
            writer?.flush()
            writer?.close()
            writer = null

            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensorManager.unregisterListener(sensorEventListener)

            stopForeground(true)
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        if(Build.VERSION.SDK_INT < 26 ){
            return
        }

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        notificationManager!!.createNotificationChannel(channel)
    }
}