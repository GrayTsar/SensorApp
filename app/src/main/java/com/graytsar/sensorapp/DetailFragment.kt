package com.graytsar.sensorapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.toolbar.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var enableLog:Boolean = false

    private lateinit var sensorManager:SensorManager
    private var sensorType:Int? = null

    private lateinit var adapter:AdapterDetail
    private val list:ArrayList<ModelDetail> = ArrayList()

    private var sensorEventListener:SensorEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorType = arguments!!.getInt("typeSensor")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val mSensor = sensorManager.getDefaultSensor(sensorType!!)

        if(savedInstanceState != null){
            enableLog = savedInstanceState.get("enableLog") as Boolean

            if(enableLog){
                requireActivity().fab.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_pause_white_24dp))
            }
        }

        val onClickListener = View.OnClickListener {
            enableLog = !enableLog
            if(enableLog){
                requireActivity().fab.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_pause_white_24dp))
                Snackbar.make(view, getString(R.string.startRecording), Snackbar.LENGTH_LONG).show()

                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TITLE, list[0].title + ".txt")
                startActivityForResult(intent, 1)
            } else if(!enableLog){
                requireActivity().fab.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_play_arrow_white_24dp))

                val intent = Intent(context, ForegroundServiceWriteFile::class.java)
                intent.putExtra("enableLog", false)
                ContextCompat.startForegroundService(requireContext(), intent)
            }
        }

        requireActivity().fab.setOnClickListener(onClickListener)

        adapter = AdapterDetail(requireActivity())

        view.recyclerDetail.layoutManager = LinearLayoutManager(requireContext())
        view.recyclerDetail.adapter = adapter

        val p = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(p)
        var points = p.widthPixels / 2.5f

        if(points < 400)
            points = 400f

        when (sensorType) {
            Sensor.TYPE_ACCELEROMETER -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorAccelerometer),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_acceleration_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitAcceleration)}",
                    getString(R.string.unitAcceleration),
                    getString(R.string.infoAccelerometer)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_MAGNETIC_FIELD ->{
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorMagneticField),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_magnet_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitMagneticField)}",
                    getString(R.string.unitMagneticField),
                    getString(R.string.infoMagneticField)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_GRAVITY -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorGravity),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_gravity_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitAcceleration)}",
                    getString(R.string.unitAcceleration),
                    getString(R.string.infoGravity)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_GYROSCOPE -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorGyroscope),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_gyroscope_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitRadiantSecond)}",
                    getString(R.string.unitRadiantSecond),
                    getString(R.string.infoGyroscope)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorLinearAcceleration),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_linearacceleration_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitAcceleration)}",
                    getString(R.string.unitAcceleration),
                    getString(R.string.infoLinearAcceleration)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorAmbientTemperature),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_temperature_white)!!.toBitmap(),
                    100f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitTemperature)}",
                    getString(R.string.unitTemperature),
                    getString(R.string.infoAmbientTemperature)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_LIGHT -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorLight),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_light_white)!!.toBitmap(),
                    100f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitLight)}",
                    getString(R.string.unitLight),
                    getString(R.string.infoLight)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_PRESSURE -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorPressure),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_pressure_white)!!.toBitmap(),
                    100f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${mSensor.name}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitPressure)}",
                    getString(R.string.unitPressure),
                    getString(R.string.infoPressure)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_RELATIVE_HUMIDITY -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorRelativeHumidity),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_humidity_white)!!.toBitmap(),
                    100f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${getString(R.string.sensorRelativeHumidity)}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitPercent)}",
                    getString(R.string.unitPercent),
                    getString(R.string.infoRelativeHumidity)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorGeomagneticRotationVector),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_rotate_white)!!.toBitmap(),
                    points,
                    3,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${getString(R.string.sensorGeomagneticRotationVector)}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange}",
                    "",
                    getString(R.string.infoGeomagneticRotationVector)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_PROXIMITY -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorProximity),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_proximity_white)!!.toBitmap(),
                    50f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${getString(R.string.sensorProximity)}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitProximity)}",
                    getString(R.string.unitProximity),
                    getString(R.string.infoProximity)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
            Sensor.TYPE_STEP_COUNTER -> {
                val model = ModelDetail(
                    mSensor,
                    getString(R.string.sensorStepCounter),
                    ContextCompat.getDrawable(context!!, R.drawable.ic_steps_white)!!.toBitmap(),
                    50f,
                    1,
                    sensorManager,
                    "${getString(R.string.labelName)}  ${getString(R.string.sensorStepCounter)}",
                    "${getString(R.string.labelVendor)}  ${mSensor.vendor}",
                    "${getString(R.string.labelVersion)}  ${mSensor.version}",
                    "${getString(R.string.labelPower)}  ${mSensor.power} ${getString(R.string.unitAmpere)}",
                    "${getString(R.string.labelMaxDelay)}  ${mSensor.maxDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMinDelay)}  ${mSensor.minDelay} ${getString(R.string.unitMicroseconds)}",
                    "${getString(R.string.labelMaxRange)}  ${mSensor.maximumRange} ${getString(R.string.unitSteps)}",
                    getString(R.string.unitSteps),
                    getString(R.string.infoStepCounter)
                )

                list.add(model)
                sensorEventListener = model.registerSensor()
            }
        }

        adapter.submitList(list)

        return view
    }

    override fun onActivityResult(request:Int, result:Int, resultData: Intent?){
        if(resultData != null && resultData.data != null){
            val fUri = resultData.data!!

            val intent = Intent(context, ForegroundServiceWriteFile::class.java).apply {
                putExtra("enableLog", true)
                putExtra("sensorType", sensorType!!)
                putExtra("title", list[0].title)
                putExtra("sensorValuesCount", list[0].sensorValuesCount)
                putExtra("csvHeader", list[0].csvHeader)
                putExtra("fUri", fUri.toString())
            }
            ContextCompat.startForegroundService(requireContext(), intent)
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
        sensorManager.unregisterListener(sensorEventListener)
        requireActivity().fab.setOnClickListener(null)


        if(enableLog){
            val intent = Intent(context, ForegroundServiceWriteFile::class.java)
            intent.putExtra("enableLog", false)
            ContextCompat.startForegroundService(requireContext(), intent)
        }

        enableLog = false
        requireActivity().fab.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_play_arrow_white_24dp))


        activity!!.collapsingToolbarLayout.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        activity!!.window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorPrimary)
        activity!!.findViewById<ImageView>(R.id.toolbarBackdrop).setImageResource(0)
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("enableLog", enableLog)
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
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
