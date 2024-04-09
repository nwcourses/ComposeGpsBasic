package com.example.composegps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.composegps.ui.theme.ComposeGpsTheme


class MainActivity : ComponentActivity(), LocationListener {

    val locationViewModel : LocationViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ComposeGpsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GpsPositionComposable()
                }
            }
        }
        checkPermissions()
    }

    fun checkPermissions() {
        if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if(it) {
                    startGps()
                } else {
                    Toast.makeText(this, "Cannot listen to GPS as permission not granted", Toast.LENGTH_LONG).show()
                }
            }
            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            startGps()
        }
    }

    @SuppressLint("MissingPermission")
    fun startGps() {
        val lMgr = getSystemService(LOCATION_SERVICE) as LocationManager
        lMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
    }

    override fun onLocationChanged(location: Location) {
        locationViewModel.latLon = LatLon(location.latitude, location.longitude)
    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    @Composable
    fun GpsPositionComposable(

    ) {
        var latLon by remember { mutableStateOf(LatLon())}

        locationViewModel.liveLatLon.observe(this) {
            latLon = it
        }
        Text("Lat ${latLon.lat} lon ${latLon.lon}")
    }
}





