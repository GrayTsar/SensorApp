package com.graytsar.sensorapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.sensor_view_card.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Home.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var listSensorEventListener:LinkedList<SensorEventListener>? = null
    private val typeList:IntArray = intArrayOf(
        Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD,
        Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LINEAR_ACCELERATION,
        Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE,
        Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        Sensor.TYPE_PROXIMITY , Sensor.TYPE_STEP_COUNTER
    )

    private lateinit var sensorManager:SensorManager
    private lateinit var linearLayout: LinearLayout
    private lateinit var listSensor:LinkedList<Sensor>
    private lateinit var listCard:LinkedList<View>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        linearLayout = view.linear_layout_sensor_card_list

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initLayout()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listSensorEventListener = registerAll()
    }

    private fun initLayout(){
        listSensor= LinkedList()
        listCard = LinkedList()

        typeList.forEach{
            val card:View = layoutInflater.inflate(R.layout.sensor_view_card, null)
            val mSensor = sensorManager.getDefaultSensor(it)
            val navController = findNavController(this)

            if(mSensor != null){
                when (mSensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        card.card_title.text = getString(R.string.sensorAccelerometer)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_acceleration_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.accelerometer)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        card.card_title.text = getString(R.string.sensorMagneticField)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPink, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_magnet_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.magneticField)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_GRAVITY -> {
                        card.card_title.text = getString(R.string.sensorGravity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPurple, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_gravity_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.gravity)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_GYROSCOPE -> {
                        card.card_title.text = getString(R.string.sensorGyroscope)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorDeepPurple, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_gyroscope_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.gyroscope)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_LINEAR_ACCELERATION -> {
                        card.card_title.text = getString(R.string.sensorLinearAcceleration)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorIndigo, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_linearacceleration_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.linearAcceleration)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                        card.card_title.text = getString(R.string.sensorAmbientTemperature)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBlue, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_temperature_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.ambient_Temperature)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_LIGHT -> {
                        card.card_title.text = getString(R.string.sensorLight)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightBlue, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_light_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.light)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_PRESSURE -> {
                        card.card_title.text = getString(R.string.sensorPressure)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorCyan, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_pressure_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.pressure)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_RELATIVE_HUMIDITY -> {
                        card.card_title.text = getString(R.string.sensorRelativeHumidity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorTeal, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_humidity_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.relativeHumidity)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                        card.card_title.text = getString(R.string.sensorGeomagneticRotationVector)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorGreen, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_rotate_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.geomagneticRotationVector)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_PROXIMITY -> {
                        card.card_title.text = getString(R.string.sensorProximity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightGreen, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_proximity_white))
                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.proximity)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                    Sensor.TYPE_STEP_COUNTER -> {
                        card.card_title.text = getString(R.string.sensorStepCounter)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLime, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_steps_white))
                        card.card_v1.text = "0"
                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            //Log.d("DBG: ", it.card_title.text.toString())
                            navController.navigate(R.id.stepCounter)
                        }

                        listSensor.add(mSensor)
                        listCard.add(card)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerAll(): LinkedList<SensorEventListener>{
        val list: LinkedList<SensorEventListener> = LinkedList()

        for(i in 0 until listSensor.count()){
            val mSensor = listSensor[i]
            val mCard = listCard[i]

            when (mSensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " m/s²"
                        mCard.card_v2.text = String.format("%.2f", it.values[1]) + " m/s²"
                        mCard.card_v3.text = String.format("%.2f", it.values[2]) + " m/s²"
                    }
                    list.add(o)
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " µT"
                        mCard.card_v2.text = String.format("%.2f", it.values[1]) + " µT"
                        mCard.card_v3.text = String.format("%.2f", it.values[2]) + " µT"
                    }
                    list.add(o)
                }
                Sensor.TYPE_GRAVITY -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " m/s²"
                        mCard.card_v2.text = String.format("%.2f", it.values[1]) + " m/s²"
                        mCard.card_v3.text = String.format("%.2f", it.values[2]) + " m/s²"
                    }
                    list.add(o)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " rad/sec"
                        mCard.card_v2.text = String.format("%.2f", it.values[1]) + " rad/sec"
                        mCard.card_v3.text = String.format("%.2f", it.values[2]) + " rad/sec"
                    }
                    list.add(o)
                }
                Sensor.TYPE_LINEAR_ACCELERATION -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " m/s²"
                        mCard.card_v2.text = String.format("%.2f", it.values[1]) + " m/s²"
                        mCard.card_v3.text = String.format("%.2f", it.values[2]) + " m/s²"
                    }
                    list.add(o)
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " C°"
                    }
                    list.add(o)
                }
                Sensor.TYPE_LIGHT -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " lux"
                    }
                    list.add(o)
                }
                Sensor.TYPE_PRESSURE -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " hPa"
                    }
                    list.add(o)
                }
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " %"
                    }
                    list.add(o)
                }
                Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0])
                        mCard.card_v2.text = String.format("%.2f", it.values[1])
                        mCard.card_v3.text = String.format("%.2f", it.values[2])
                    }
                    list.add(o)
                }
                Sensor.TYPE_PROXIMITY -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0]) + " cm"
                    }
                    list.add(o)
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    val o = SensorSingleton.registerSelected(sensorManager, mSensor) {
                        mCard.card_v1.text = String.format("%.2f", it.values[0])
                    }
                    list.add(o)
                }

            }
        }
        return list
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()

        if(listSensorEventListener != null){
            for (sensorEventListener in listSensorEventListener!!) {
                sensorManager.unregisterListener(sensorEventListener)
            }
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
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
