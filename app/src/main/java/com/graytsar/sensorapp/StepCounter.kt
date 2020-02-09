package com.graytsar.sensorapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_acceleration.view.*
import kotlinx.android.synthetic.main.sensor_details.view.*
import kotlinx.android.synthetic.main.sensor_view_card.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StepCounter.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StepCounter.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepCounter : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var enableLog:Boolean = false
    private var logData:String = ""
    private var sEventListener: SensorEventListener? = null

    private lateinit var sensorManager:SensorManager
    private lateinit var mSensor:Sensor
    private lateinit var mData:LineData
    private lateinit var mChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step_counter, container, false)

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(savedInstanceState != null){
            enableLog = savedInstanceState.get("enableLog") as Boolean
            logData =  savedInstanceState.get("logData") as String

            if(enableLog){
                view.card.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_pause_white_24dp))
            }
        }

        view.card.floatingActionButton.show()
        view.card.floatingActionButton.setOnClickListener {
            enableLog = !enableLog
            if(enableLog){
                view.card.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_pause_white_24dp))
                logData = "timestamp,x,y,z,${mSensor.name},Vendor:${mSensor.vendor},Version:${mSensor.version},Power:${mSensor.power},MaxDelay:${mSensor.maxDelay},MinDelay:${mSensor.minDelay},MaxRange:${mSensor.maximumRange}"
                Snackbar.make(view, getString(R.string.startRecording), Snackbar.LENGTH_LONG).show()
            } else if(!enableLog){
                view.card.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_play_arrow_white_24dp))

                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.sensorStepCounter) + ".txt")
                startActivityForResult(intent, 1)
            }
        }

        view.card.imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_steps_white))

        activity!!.toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLime, null))
        view.card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLime, null))
        view.card.card_title.visibility = TextView.GONE
        view.card_v2.visibility = TextView.GONE
        view.card_v3.visibility = TextView.GONE
        //view.card.card_title.text = getString(R.string.sensorStepCounter)
        view.detail.sensor_text_name.text = "${getString(R.string.labelName)} ${mSensor.name}"
        view.detail.sensor_text_vendor.text = "${getString(R.string.labelVendor)} ${mSensor.vendor}"
        view.detail.sensor_text_version.text = "${getString(R.string.labelVersion)} ${mSensor.version}"
        view.detail.sensor_text_power.text = "${getString(R.string.labelPower)} ${mSensor.power} ${getString(R.string.unitAmpere)}"
        view.detail.sensor_text_max_delay.text = "${getString(R.string.labelMaxDelay)} ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}"
        view.detail.sensor_text_min_delay.text = "${getString(R.string.labelMinDelay)} ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}"
        view.detail.sensor_text_max_range.text = "${getString(R.string.labelMaxRange)} ${mSensor.maximumRange} ${getString(R.string.unitSteps)}"
        view.detail.sensor_text_information.text = getString(R.string.infoStepCounter)

        mData = LineData()
        mChart = view.chart
        SensorSingleton.setupChart(1, mData, mChart)

        // Inflate the layout for this fragment
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var i = 0
        sEventListener = SensorSingleton.registerSelected(sensorManager, mSensor) {
            view.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitSteps)}"

            if(enableLog){
                logData += "\n${it.timestamp},${it.values[0]}"
            }

            SensorSingleton.addPointsToChart(i, mData, mChart, it.values, safety = true)
            i++
        }
    }

    override fun onActivityResult(request:Int, result:Int, resultData: Intent?){
        if(resultData != null && resultData.data != null){
            val fUri = resultData.data!!
            val pfd: ParcelFileDescriptor = activity!!.applicationContext.contentResolver.openFileDescriptor(fUri, "w")!!
            val outStream = FileOutputStream(pfd.fileDescriptor)
            outStream.write(logData.toByteArray())
            outStream.close()
            logData = ""
        }
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("enableLog", enableLog)
        outState.putString("logData", logData)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if(sEventListener != null){
            val sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensorManager.unregisterListener(sEventListener)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StepCounter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StepCounter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
