package com.example.composegps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class LatLon(var lat: Double=51.05, var lon: Double=-0.72)
class LocationViewModel : ViewModel() {
    var latLon =  LatLon()
        set(newVal) {
            field = newVal
            liveLatLon.value = newVal
        }

    var liveLatLon = MutableLiveData<LatLon>()
}