package com.example.googlemap
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize geocoder
        geocoder = Geocoder(this, Locale.getDefault())

        // Initialize map fragment
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Initialize search view
        searchView = findViewById(R.id.searchView)
        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchLocation(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // You can implement suggestions here if needed
                return false
            }
        })
    }

    private fun searchLocation(locationName: String) {
        try {
            val addressList: List<Address>? = geocoder.getFromLocationName(locationName, 1)

            if (addressList.isNullOrEmpty()) {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
                return
            }

            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)

            // Clear previous markers
            googleMap.clear()

            // Add marker for the searched location
            googleMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(locationName))

            // Move camera to the searched location with zoom
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        } catch (e: IOException) {
            Toast.makeText(this, "Error searching location: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(gmap: GoogleMap) {
        googleMap = gmap

        // Set default location (your original marker)
        val defaultLocation = LatLng(12.8367213, 77.6570388)
        googleMap.addMarker(MarkerOptions().position(defaultLocation).title("Default Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15F))

        // Enable zoom controls
        googleMap.uiSettings.isZoomControlsEnabled = true
    }
}