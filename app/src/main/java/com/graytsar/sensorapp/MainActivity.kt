package com.graytsar.sensorapp

import android.hardware.Sensor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) //replace toolbar

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


        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged( controller: NavController,  destination: NavDestination, arguments: Bundle? ) {
        val bar = supportActionBar!!
        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController:NavController = f.navController //for fragment switch

        when (destination.id) {
            R.id.home -> {
                bar.title = getString(R.string.home)
            }
            R.id.accelerometer -> {
                bar.title = getString(R.string.sensorAccelerometer)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_ACCELEROMETER) })
            }
            R.id.magneticField -> {
                bar.title = getString(R.string.sensorMagneticField)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_MAGNETIC_FIELD) })
            }
            R.id.gravity -> {
                bar.title = getString(R.string.sensorGravity)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GRAVITY) })
            }
            R.id.gyroscope -> {
                bar.title = getString(R.string.sensorGyroscope)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GYROSCOPE) })
            }
            R.id.linearAcceleration -> {
                bar.title = getString(R.string.sensorLinearAcceleration)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_LINEAR_ACCELERATION) })
            }
            R.id.ambient_Temperature -> {
                bar.title = getString(R.string.sensorAmbientTemperature)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_AMBIENT_TEMPERATURE) })
            }
            R.id.light -> {
                bar.title = getString(R.string.sensorLight)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_LIGHT) })
            }
            R.id.pressure -> {
                bar.title = getString(R.string.sensorPressure)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_PRESSURE) })
            }
            R.id.relativeHumidity -> {
                bar.title = getString(R.string.sensorRelativeHumidity)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_RELATIVE_HUMIDITY) })
            }
            R.id.geomagneticRotationVector -> {
                bar.title = getString(R.string.sensorGeomagneticRotationVector)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) })
            }
            R.id.proximity -> {
                bar.title = getString(R.string.sensorProximity)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_PROXIMITY) })
            }
            R.id.stepCounter -> {
                bar.title = getString(R.string.sensorStepCounter)
                navController.navigate(R.id.detailFragment, Bundle().apply { putInt("typeSensor", Sensor.TYPE_STEP_COUNTER) })
            }

        }
    }


}
