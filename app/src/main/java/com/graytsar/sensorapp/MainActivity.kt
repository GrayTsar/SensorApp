package com.graytsar.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NavController.OnDestinationChangedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        this.setSupportActionBar(toolbar) //replace toolbar

        val drawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView:NavigationView = findViewById(R.id.navigation_view)

        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch



        //hamburger and back arrow icon functionality
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration) //link with toolbar
        navigationView.setupWithNavController(navController) //link with fragments
        navigationView.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        when(p0.itemId){
            R.id.home ->{
                navController.navigate(p0.itemId)
            }
            R.id.accelerometer ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) //if sensor is available then navigate to detail fragment
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show() //else show error message
            }
            R.id.magneticField ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
                    navController.navigate(p0.itemId)
                }
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.gravity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
                    navController.navigate(p0.itemId)
                }
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.gyroscope ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.linearAcceleration ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.ambient_Temperature ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.light ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.pressure ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.relativeHumidity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.geomagneticRotationVector ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.proximity ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
            R.id.stepCounter ->{
                if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    navController.navigate(p0.itemId)
                else
                    Snackbar.make(drawer_layout, p0.title.toString() + " ${getString(R.string.missingSensorBottomBar)}", Snackbar.LENGTH_LONG).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestinationChanged( controller: NavController,  destination: NavDestination, arguments: Bundle? ) {
        val bar = supportActionBar!!

        when (destination.id) {
            R.id.home -> {
                bar.title = getString(R.string.home)
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarGreen, null))
            }
            R.id.accelerometer ->{
                bar.title = getString(R.string.sensorAccelerometer)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
            }
            R.id.magneticField -> {
                bar.title = getString(R.string.sensorMagneticField)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPink, null))
            }
            R.id.gravity -> {
                bar.title = getString(R.string.sensorGravity)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPurple, null))
            }
            R.id.gyroscope -> {
                bar.title = getString(R.string.sensorGyroscope)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorDeepPurple, null))
            }
            R.id.linearAcceleration -> {
                bar.title = getString(R.string.sensorLinearAcceleration)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorIndigo, null))
            }
            R.id.ambient_Temperature -> {
                bar.title = getString(R.string.sensorAmbientTemperature)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBlue, null))
            }
            R.id.light -> {
                bar.title = getString(R.string.sensorLight)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightBlue, null))
            }
            R.id.pressure -> {
                bar.title = getString(R.string.sensorPressure)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorCyan, null))
            }
            R.id.relativeHumidity -> {
                bar.title = getString(R.string.sensorRelativeHumidity)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorTeal, null))
            }
            R.id.geomagneticRotationVector -> {
                bar.title = getString(R.string.sensorGeomagneticRotationVector)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorGreen, null))
            }
            R.id.proximity -> {
                bar.title = getString(R.string.sensorProximity)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightGreen, null))
            }
            R.id.stepCounter -> {
                bar.title = getString(R.string.sensorStepCounter)
                //bar.title = ""
                toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLime, null))
            }
            else -> getString(R.string.home)
        }
    }


}
