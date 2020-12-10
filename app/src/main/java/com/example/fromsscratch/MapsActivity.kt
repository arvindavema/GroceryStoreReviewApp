package com.example.fromsscratch

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MapsActivity :
    AppCompatActivity(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback,
    OnMapReadyCallback,
    GoogleMap.OnMapLoadedCallback,
    GoogleMap.OnPoiClickListener {

    private lateinit var mMap: GoogleMap
    lateinit var latitude:String
    lateinit var longitude:String
    lateinit var id:String
    lateinit  var name:String
    lateinit var address:String
    internal var isMarkerCenter = true
    internal var marker: Marker? = null

    //Creating member variables of FirebaseAuth
    private var mAuth: FirebaseAuth?=null

    //Creating member variables of FirebaseDatabase and DatabaseReference
    private var mDBInstance: FirebaseDatabase?=null
    private var mStoresDB: DatabaseReference?=null
    private var mUserReviewsDB: DatabaseReference?=null
    private lateinit var placesClient: PlacesClient
    //Creating member variable for userId and emailAddress
    private var userId:String?=null
    private var emailAddress:String?=null

    private var permissionDenied = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_maps)

        // Initialize the SDK
        // Create a new PlacesClient instance
        Places.initialize(applicationContext, "AIzaSyBh3A4SsoTM3lB8p1J5eIqDEOoXkpzA5DM")
        placesClient = Places.createClient(applicationContext)

        //Get Firebase Instances
        mAuth=FirebaseAuth.getInstance()
        userId  = mAuth!!.currentUser!!.uid

        //Get instance of FirebaseDatabase
        mDBInstance = FirebaseDatabase.getInstance()

        mStoresDB = mDBInstance!!.getReference("Stores")
        mUserReviewsDB = mDBInstance!!.getReference("users").child(userId!!).child("reviews")


        name=intent.getStringExtra("name")
        latitude=intent.getStringExtra("lat")
        longitude=intent.getStringExtra("lon")
        id=intent.getStringExtra("id")
        address=intent.getStringExtra("add")


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap ?: return
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        googleMap.setOnPoiClickListener(this)
        enableMyLocation()

        val double1: Double? = latitude.toDouble()
        val double2: Double? = longitude.toDouble()
        val sydney = LatLng(double1!!, double2!!)

        var placeFields = Collections.singletonList(Place.Field.NAME) as List<Place.Field>
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {

            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                        toast("Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}")
                    }
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {   toast( "Place not found: ${exception.statusCode}")                    }
                }
            }
        } else {
            // A local method to request required permissions;
            ask()
        }

        //setting and adding a marker to a position
        marker = mMap?.addMarker(
            MarkerOptions().position(sydney)
                .title(name)
                .snippet(address))

        mMap.setOnInfoWindowClickListener {
            val windowManager2 =  getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val layoutInflater =  getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val LAYOUT_FLAG: Int
            LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {   WindowManager.LayoutParams.TYPE_PHONE }

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.info_window, null)

            val builder = AlertDialog.Builder(this)  .setView(mDialogView)
            builder.setTitle(name)
            builder.setMessage(address)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,  android.R.string.yes, Toast.LENGTH_SHORT).show()
                val body=mDialogView.findViewById<EditText>(R.id.commentBox)
                val ratingBar =mDialogView.findViewById<RatingBar>(R.id.rbar)
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,  android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
            this.closeContextMenu()
        }
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
    }

    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ask()
        }
    }

    override fun onMapLoaded() {
        TODO("Not yet implemented")
    }

    //Requesting Permissions
    private fun ask() {  requestPermissions( PERMISSIONS, REQUEST_CODE) }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE ->   {
                if (grantResults.isNotEmpty() &&   grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation()
                } else {
                    permissionDenied = true
                    toast("You will need to provide access to your location!")
                }
            }
        }
    } // End onRequest override

    // Action when the button at top right is clicked on a map
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    // Action when blue dot on map at your location is clicled
    override fun onMyLocationClick(pOI: Location) {
        Toast.makeText(this, "Current location:\n$pOI", Toast.LENGTH_LONG).show()
    }

    // On click for Points of interests automatically shown by google maps
    override fun onPoiClick(p: PointOfInterest) {
        val placeId = p.placeId
        val placeFields = listOf(Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        var req = placesClient.fetchPlace(request)
        req.addOnSuccessListener {
           val poi = it.place
           Toast.makeText(this,
               "Clicked: ${it.place.name},  Place ID:${poi.id},   Address:${poi.address.toString()} ",  Toast.LENGTH_LONG).show()

            showReviewForm(poi)
        }

        req.addOnFailureListener {
            if(it is ApiException){
                toast("Opps! Something went wrong")
                val statusCode = it.statusCode
                Log.e("PROJ", "${statusCode}: Place not found: ${it.message}")
            }
        }


    }

    private fun showReviewForm(place: Place){
        var alertDialog = AlertDialog.Builder(this). create()
        alertDialog.setTitle(place.name)
        alertDialog.setMessage(place.address)
        alertDialog.setButton("Leave a Review!", DialogInterface.OnClickListener { dialog, which ->
            var intent = Intent(this@MapsActivity, AddReviewActivity::class.java)

            //store name, address and placeId incase you need to referernce the Place object again
            intent.putExtra("name", place.name)
            intent.putExtra("address", place.address)
            intent.putExtra("placeId", place.id)
            intent.putExtra("authonName", mAuth!!.currentUser!!.displayName )
            intent.putExtra("user_id", mAuth!!.currentUser!!.uid)


            //TODO implement AddReviewActivity. just make it a form lik e
            //TODO ayomi did that one time.
            toast("Don;t worry, this is just unimplemented")
            startActivity(intent)
        })
    }
    private fun toast(msg: String){
        Toast.makeText(this@MapsActivity, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val PERMISSIONS = arrayOf( ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        const val REQUEST_CODE = 99
    }
}