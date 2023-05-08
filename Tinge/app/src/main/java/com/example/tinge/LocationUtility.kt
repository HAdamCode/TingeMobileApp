package com.example.tinge

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsStates
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException

class LocationUtility(private val context: Context) {
    companion object {
        private const val LOG_TAG = "448.LocationUtility"
    }

    private val mCurrentLocationStateFlow: MutableStateFlow<Location?> = MutableStateFlow(null)
    val currentLocationStateFlow: StateFlow<Location?>
        get() = mCurrentLocationStateFlow.asStateFlow()

    private val mCurrentAddressStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    val currentAddressStateFlow: StateFlow<String>
        get() = mCurrentAddressStateFlow.asStateFlow()

    private val geocoder = Geocoder(context)

    private val mIsLocationAvailableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentIsLocationAvailableFlow: StateFlow<Boolean>
        get() = mIsLocationAvailableStateFlow.asStateFlow()

    suspend fun getAddress(location: Location?) {
        val addressTextBuilder = StringBuilder()
        if (location != null) {
            try {
                val addresses = geocoder.getFromLocation(
                    location.latitude, location.longitude, 1
                )
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    for (i in 0..address.maxAddressLineIndex) {
                        if (i > 0) {
                            addressTextBuilder.append("\n")
                        }
                        addressTextBuilder.append(address.getAddressLine(i))
                    }
                }
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Error getting address", e)
            }
        }
        mCurrentAddressStateFlow.update { addressTextBuilder.toString() }
    }

    fun checkPermissionAndGetLocation(
        activity: Activity, permissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        if (activity.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || activity.checkSelfPermission(
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, ACCESS_COARSE_LOCATION
                )
            ) {
                Toast.makeText(
                    activity,
                    "We must access your location to plot where you are",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                permissionLauncher.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
            }
        }
    }

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0L).setMaxUpdates(1).build()

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            mCurrentLocationStateFlow.value = lastLocation
        }
    }

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun verifyLocationSettingsStates(states: LocationSettingsStates?) {
        mIsLocationAvailableStateFlow.update { states?.isLocationUsable ?: false }
    }
}