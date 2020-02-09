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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.sensor_view_card.view.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

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

    private val typeList:IntArray = intArrayOf(
        Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD,
        Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LINEAR_ACCELERATION,
        Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE,
        Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        Sensor.TYPE_PROXIMITY , Sensor.TYPE_STEP_COUNTER
    )

    private lateinit var arListSensorEventListener:ArrayList<SensorEventListener>
    private lateinit var sensorManager:SensorManager
    private lateinit var linearLayout: LinearLayout
    private lateinit var mapSensorView:LinkedHashMap<Sensor, View>

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

        MobileAds.initialize(this.context)
        val mAdView = view.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAll()
    }

    private fun initLayout(){
        mapSensorView = LinkedHashMap()

        typeList.forEach{
            val card:View = layoutInflater.inflate(R.layout.sensor_view_card, null)
            val mSensor = sensorManager.getDefaultSensor(it)
            val f = activity!!.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController: NavController = f.navController //for fragment switch

            if(mSensor != null){
                when (mSensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        card.card_title.text = getString(R.string.sensorAccelerometer)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_acceleration_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.accelerometer)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        card.card_title.text = getString(R.string.sensorMagneticField)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPink, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_magnet_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.magneticField)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_GRAVITY -> {
                        card.card_title.text = getString(R.string.sensorGravity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPurple, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_gravity_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.gravity)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_GYROSCOPE -> {
                        card.card_title.text = getString(R.string.sensorGyroscope)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorDeepPurple, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_gyroscope_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.gyroscope)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_LINEAR_ACCELERATION -> {
                        card.card_title.text = getString(R.string.sensorLinearAcceleration)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorIndigo, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_linearacceleration_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.linearAcceleration)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                        card.card_title.text = getString(R.string.sensorAmbientTemperature)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBlue, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_temperature_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            navController.navigate(R.id.ambient_Temperature)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_LIGHT -> {
                        card.card_title.text = getString(R.string.sensorLight)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightBlue, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_light_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            navController.navigate(R.id.light)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_PRESSURE -> {
                        card.card_title.text = getString(R.string.sensorPressure)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorCyan, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_pressure_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            navController.navigate(R.id.pressure)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_RELATIVE_HUMIDITY -> {
                        card.card_title.text = getString(R.string.sensorRelativeHumidity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorTeal, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_humidity_white))
                        linearLayout.addView(card)

                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE

                        card.setOnClickListener {
                            navController.navigate(R.id.relativeHumidity)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                        card.card_title.text = getString(R.string.sensorGeomagneticRotationVector)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorGreen, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_rotate_white))
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.geomagneticRotationVector)
                        }

                        mapSensorView[mSensor] = card
                    }
                    Sensor.TYPE_PROXIMITY -> {
                        card.card_title.text = getString(R.string.sensorProximity)
                        card.backView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightGreen, null))
                        card.imageView.setImageDrawable(ContextCompat.getDrawable(card.context, R.drawable.ic_proximity_white))
                        card.card_v2.visibility = TextView.GONE
                        card.card_v3.visibility = TextView.GONE
                        linearLayout.addView(card)

                        card.setOnClickListener {
                            navController.navigate(R.id.proximity)
                        }

                        mapSensorView[mSensor] = card
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
                            navController.navigate(R.id.stepCounter)
                        }

                        mapSensorView[mSensor] = card
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerAll() {
        arListSensorEventListener = ArrayList()

        mapSensorView.forEach {itMap ->
            when(itMap.key.type){
                Sensor.TYPE_ACCELEROMETER -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2]) + " ${getString(R.string.unitAcceleration)}"
                    })
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitMagneticField)}"
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1]) + " ${getString(R.string.unitMagneticField)}"
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2]) + " ${getString(R.string.unitMagneticField)}"
                    })
                }
                Sensor.TYPE_GRAVITY -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2]) + " ${getString(R.string.unitAcceleration)}"
                    })
                }
                Sensor.TYPE_GYROSCOPE -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitRadiantSecond)}"
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1]) + " ${getString(R.string.unitRadiantSecond)}"
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2]) + " ${getString(R.string.unitRadiantSecond)}"
                    })
                }
                Sensor.TYPE_LINEAR_ACCELERATION -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1]) + " ${getString(R.string.unitAcceleration)}"
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2]) + " ${getString(R.string.unitAcceleration)}"
                    })
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitTemperature)}"
                    })
                }
                Sensor.TYPE_LIGHT -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitLight)}"
                    })
                }
                Sensor.TYPE_PRESSURE -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitPressure)}"
                    })
                }
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitPercent)}"
                    })
                }
                Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0])
                        itMap.value.card_v2.text = String.format("%.2f", it.values[1])
                        itMap.value.card_v3.text = String.format("%.2f", it.values[2])
                    })
                }
                Sensor.TYPE_PROXIMITY -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitProximity)}"
                    })
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    arListSensorEventListener.add(SensorSingleton.registerSelected(sensorManager, itMap.key) {
                        itMap.value.card_v1.text = String.format("%.2f", it.values[0]) + " ${getString(R.string.unitSteps)}"
                    })
                }
            }
        }
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

        for (sensorEventListener in arListSensorEventListener) {
            sensorManager.unregisterListener(sensorEventListener)
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
