package com.graytsar.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.Exception
import kotlin.collections.ArrayList

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
class Home : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //easy for loop, exclude android wear sensor, because app is only for smartphones
    //wears sensors do not exists in smartphones
    private val typeList:IntArray = intArrayOf(
        Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD,
        Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LINEAR_ACCELERATION,
        Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE,
        Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        Sensor.TYPE_PROXIMITY , Sensor.TYPE_STEP_COUNTER
    )

    //keep references to delete sensorEventListener when switching view
    //an Exceptions is thrown if there are to many Listeners Registered
    //to reproduce Exception, Start from Home and go to any sensor and back repeatedly
    private var arListSensorEventListener:ArrayList<SensorEventListener> = ArrayList()
    private lateinit var sensorManager:SensorManager

    private val list = ArrayList<ModelSensor>()
    private lateinit var adapter:AdapterSensor


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
        // Inflate the layout for this fragment"default"
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = AdapterSensor(requireActivity())

        val navigationView:NavigationView = activity!!.findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        view.recyclerHome.layoutManager = LinearLayoutManager(context)
        view.recyclerHome.adapter = adapter

        initLayout()
        adapter.submitList(list)

        return view
    }



    private fun initLayout(){
        typeList.forEach{
            val mSensor = sensorManager.getDefaultSensor(it)
            val f = activity!!.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController: NavController = f.navController //for fragment switch

            if(mSensor != null){
                when (mSensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorAccelerometer), ContextCompat.getDrawable(context!!, R.drawable.ic_acceleration_white)!!.toBitmap(), 3, getString(R.string.unitAcceleration), sensorManager, navController)
                        list.add(model)
                        //set background color in adapter
                        //assign bitmap in adapter

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorMagneticField), ContextCompat.getDrawable(context!!, R.drawable.ic_magnet_white)!!.toBitmap(), 3, getString(R.string.unitMagneticField), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_GRAVITY -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorGravity), ContextCompat.getDrawable(context!!, R.drawable.ic_gravity_white)!!.toBitmap(), 3, getString(R.string.unitAcceleration), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_GYROSCOPE -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorGyroscope), ContextCompat.getDrawable(context!!, R.drawable.ic_gyroscope_white)!!.toBitmap(), 3, getString(R.string.unitRadiantSecond), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_LINEAR_ACCELERATION -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorLinearAcceleration), ContextCompat.getDrawable(context!!, R.drawable.ic_linearacceleration_white)!!.toBitmap(),3, getString(R.string.unitAcceleration), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorAmbientTemperature), ContextCompat.getDrawable(context!!, R.drawable.ic_temperature_white)!!.toBitmap(), 1, getString(R.string.unitTemperature), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_LIGHT -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorLight), ContextCompat.getDrawable(context!!, R.drawable.ic_light_white)!!.toBitmap(), 1, getString(R.string.unitLight), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_PRESSURE -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorPressure), ContextCompat.getDrawable(context!!, R.drawable.ic_pressure_white)!!.toBitmap(),1, getString(R.string.unitPressure), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_RELATIVE_HUMIDITY -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorRelativeHumidity), ContextCompat.getDrawable(context!!, R.drawable.ic_humidity_white)!!.toBitmap(),1, getString(R.string.unitPercent), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorGeomagneticRotationVector), ContextCompat.getDrawable(context!!, R.drawable.ic_rotate_white)!!.toBitmap(),3, "", sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_PROXIMITY -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorProximity), ContextCompat.getDrawable(context!!, R.drawable.ic_proximity_white)!!.toBitmap(), 1, getString(R.string.unitProximity), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
                    Sensor.TYPE_STEP_COUNTER -> {
                        val model = ModelSensor(mSensor, getString(R.string.sensorStepCounter), ContextCompat.getDrawable(context!!, R.drawable.ic_steps_white)!!.toBitmap(), 1, getString(R.string.unitSteps), sensorManager, navController)
                        list.add(model)

                        //arListSensorEventListener.add(model.registerSensor())
                    }
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

    override fun onStart() {
        super.onStart()

        list.forEach {
            arListSensorEventListener.add(it.registerSensor())
        }
    }

    override fun onPause() {
        super.onPause()

        for (sensorEventListener in arListSensorEventListener) {
            sensorManager.unregisterListener(sensorEventListener)
        }
        arListSensorEventListener.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        list.clear()
        for (sensorEventListener in arListSensorEventListener) {
            sensorManager.unregisterListener(sensorEventListener)
        }
        arListSensorEventListener.clear()
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

    //drawer layout menu clicked
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val f = activity!!.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch
        val sensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val d = activity!!.findViewById<DrawerLayout>(R.id.drawer_layout).drawer_layout

        when(p0.itemId){
            R.id.home ->{
                navController.navigate(p0.itemId)
            }
            R.id.accelerometer ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) //if sensor is available then navigate to detail fragment
                {
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_ACCELEROMETER) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show() //else show error message
            }
            R.id.magneticField ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_MAGNETIC_FIELD) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.gravity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GRAVITY) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.gyroscope ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GYROSCOPE) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.linearAcceleration ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_LINEAR_ACCELERATION) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.ambient_Temperature ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_AMBIENT_TEMPERATURE) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.light ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_LIGHT) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.pressure ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_PRESSURE) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.relativeHumidity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_RELATIVE_HUMIDITY) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.geomagneticRotationVector ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.proximity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_PROXIMITY) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.stepCounter ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
                    navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_STEP_COUNTER) })
                }
                else
                    Snackbar.make(d, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            else -> {
                Snackbar.make(drawer_layout, "unknown", Snackbar.LENGTH_LONG).show()
            }
        }
        d.closeDrawer(GravityCompat.START)
        return true
    }
}
