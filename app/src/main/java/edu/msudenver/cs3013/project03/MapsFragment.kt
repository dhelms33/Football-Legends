package edu.msudenver.cs3013.project03

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import edu.msudenver.cs3013.project03.databinding.ActivityMapsBinding
import java.util.Arrays

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var marker: Marker? = null
    private var carMarker: Marker? = null

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var button: Button // Define the button variable here
    private lateinit var locationText: String


    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = ActivityMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the button here
        button = view.findViewById(R.id.maps_mark_location_button)

        // Prepare the ViewModel here
//        prepareViewModel()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.maps_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize the requestPermissionLauncher
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    context?.let {
                        getLastLocation(onLocation = { location ->
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                        })
                    }
                } else {
                    // if should show rationale, show it
                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                        showPermissionRationale {
                            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
                        }
                    }
                }
            }

        button.setOnClickListener {
            if (hasLocationPermission()) {
                findPlaces()
            }
        }
    }

    private fun hasLocationPermission(): Boolean =
        // Check if ACCESS_FINE_LOCATION permission is granted
        ContextCompat.checkSelfPermission(
            requireContext(),
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap.apply {
            setOnMapClickListener { latLng ->
                addOrMoveSelectedPositionMarker(latLng)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(onLocation: (location: LatLng) -> Unit) {
        // Fused location last location with addOnFailureListener and addOnCanceledListener listeners added
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLocation = LatLng(it.latitude, it.longitude)
                    updateMapLocation(currentLocation)
                    addMarkerAtLocation(currentLocation, "Current Location")
                    // Zoom in
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

                    // Call the lambda with the latitude and longitude values
                    onLocation(currentLocation)
                }
            }
    }

    private fun addMarkerAtLocation(
        location: LatLng,
        title: String,
        markerIcon: BitmapDescriptor? = null
    ) = mMap.addMarker(
        MarkerOptions()
            .title(title)
            .position(location)
            .apply {
                markerIcon?.let { icon(markerIcon) }
            }
    )

    private fun markParkedCar() {
        getLastLocation { location ->
            val userLocation = updateMapLocationWithMarker(location)
            carMarker?.remove()
            carMarker = addMarkerAtLocation(
                userLocation,
                "Your Car",
                getBitmapDescriptorFromVector(
                    requireContext(),
                    R.drawable.baseline_sports_soccer_24
                )
            )
            saveLocation(userLocation)
        }
    }

    private fun saveLocation(latLng: LatLng) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("latitude", latLng.latitude.toString())
            putString("longitude", latLng.longitude.toString())
            apply()
        }
    }

    private fun updateMapLocation(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 7f))
    }

    private fun updateMapLocationWithMarker(location: LatLng): LatLng {
        val userLocation = LatLng(location.latitude, location.longitude)
        updateMapLocation(userLocation)
        marker?.remove()
        marker = addMarkerAtLocation(userLocation, "You")
        return userLocation
    }
    @SuppressLint("MissingPermission")
    private fun findPlaces() {
        // Assume we have map set up, and have requested permissions for fine location

// Get the API key from the manifest
        val pm: PackageManager = requireContext().packageManager
        val appInfo =
            pm.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)
        val metaData = appInfo.metaData
        val key = "com.google.android.geo.API_KEY"
        val apiKey = metaData.getString(key)!!

        Places.initialize(requireContext(), apiKey)
        val placesClient: PlacesClient = Places.createClient(requireContext())

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)

                    // Define a radius in meters
                    val radiusInMeters = 5000.0 // 5 km

                    // Convert radius to degrees
                    val radiusInDegrees = radiusInMeters / 111000

                    // Create LatLngBounds
                    val southwest = LatLng(
                        currentLocation.latitude - radiusInDegrees,
                        currentLocation.longitude - radiusInDegrees
                    )
                    val northeast = LatLng(
                        currentLocation.latitude + radiusInDegrees,
                        currentLocation.longitude + radiusInDegrees
                    )
                    val bounds = RectangularBounds.newInstance(southwest, northeast)

                    val request = FindAutocompletePredictionsRequest.builder()
                        .setLocationBias(bounds)
                        .setCountry("US")
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setSessionToken(AutocompleteSessionToken.newInstance())
                        .setQuery("Park")
                        .build()

                    placesClient.findAutocompletePredictions(request)
                        .addOnSuccessListener { response ->
                            for (prediction in response.autocompletePredictions) {
                                Log.i("MapFragment", prediction.placeId)
                                Log.i("MapFragment", prediction.getPrimaryText(null).toString())

                                // Fetch the place details
                                val placeId = prediction.placeId
                                val placeFields: List<Place.Field> = Arrays.asList(
                                    Place.Field.ID,
                                    Place.Field.NAME,
                                    Place.Field.LAT_LNG
                                )
                                val fetchPlaceRequest =
                                    FetchPlaceRequest.newInstance(placeId, placeFields)

                                placesClient.fetchPlace(fetchPlaceRequest)
                                    .addOnSuccessListener { fetchPlaceResponse ->
                                        val place = fetchPlaceResponse.place

                                        // Add a marker for each park
                                        place.latLng?.let {
                                            addMarkerAtLocation(it, place.name.toString())
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        if (exception is ApiException) {
                                            Log.e(
                                                "MapFragment",
                                                "Place not found: " + exception.statusCode
                                            )
                                        }
                                    }
                            }
                        }
                        .addOnFailureListener { exception ->
                            if (exception is ApiException) {
                                Log.e("MapFragment", "Place not found: " + exception.statusCode)
                            }
                        }
                }
            }
    }
//    private fun prepareViewModel() {
//        val locationViewModel =
//            ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)
//        updateText(locationViewModel.location.value ?: "")
//
//        // Set click listener for the button to call markParkedCar function
//        button.setOnClickListener {
//            markParkedCar()
//            getLastLocation { location ->
//                locationText =
//                    "Latitude: ${location.latitude}, Longitude: ${location.longitude}" //was not calling getLastLocation inside of prepareviewmodel
//                locationViewModel.updateLocation(locationText)
//            }
//        }
//    }

//    private fun updateText(location: String) {
//        view?.findViewById<TextView>(R.id.mapsFragment)?.text = location
//    }


    private fun showPermissionRationale(
        positiveAction: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("Location permission")
            .setMessage("We need your permission to find your current position")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                positiveAction()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun addOrMoveSelectedPositionMarker(latLng: LatLng) {
        if (marker == null) {
            marker = addMarkerAtLocation(
                latLng,
                "Park here",
                getBitmapDescriptorFromVector(
                    requireContext(),
                    R.drawable.baseline_sports_soccer_24
                )
            )
        } else {
            marker?.apply { position = latLng }
        }
    }

    private fun getBitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val bitmap =
            ContextCompat.getDrawable(context, vectorDrawableResourceId)?.let { vectorDrawable ->
                vectorDrawable.setBounds(
                    0,
                    0,
                    vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight
                )
                val drawableWithTint = DrawableCompat.wrap(vectorDrawable)
                DrawableCompat.setTint(drawableWithTint, Color.RED)
                val bitmap = Bitmap.createBitmap(
                    vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawableWithTint.draw(canvas)
                bitmap
            }
        return bitmap?.let {
            BitmapDescriptorFactory.fromBitmap(it)
                .also { bitmap?.recycle() }
        }
    }


    // Assume we have map set up, and have requested permissions for fine location
//    val placesClient: PlacesClient = Places.createClient(requireContext())


}







