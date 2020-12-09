package com.example.fromsscratch

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMapLoadedCallback {

    private lateinit var mMap: GoogleMap
    lateinit var latitude:String
    lateinit var longitude:String
    lateinit var id:String
    lateinit  var name:String
    lateinit var address:String
    internal var isMarkerCenter = true
    internal var marker: Marker? = null
    private val markerIconSize = 60

    //Creating member variables of FirebaseAuth
    private var mAuth: FirebaseAuth?=null

    //Creating member variables of FirebaseDatabase and DatabaseReference
    private var mFirebaseDatabaseInstances: FirebaseDatabase?=null
    private var mFirebaseDatabase: DatabaseReference?=null

    //Creating member variable for userId and emailAddress
    private var userId:String?=null
    private var emailAddress:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_maps)

        //Get Firebase Instances
        mAuth=FirebaseAuth.getInstance()


        //Get instance of FirebaseDatabase
        mFirebaseDatabaseInstances= FirebaseDatabase.getInstance()



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


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val double1: Double? = latitude.toDouble()
        val double2: Double? = longitude.toDouble()
        val sydney = LatLng(double1!!, double2!!)

        marker = mMap?.addMarker(
            MarkerOptions().position(sydney)
                .title(name).snippet(address)
        )



        //    mMap.setInfoWindowAdapter(mFirebaseDatabaseInstances?.let {
        //  CustomInfoWindowForGoogleMap(this,
        //       it
        //   )
        //  })
        mMap.setOnInfoWindowClickListener {

            //  val dialog = Dialog(applicationContext)


            val windowManager2 =
                getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val layoutInflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


            val LAYOUT_FLAG: Int
            LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.info_window, null)

            val builder = AlertDialog.Builder(this)  .setView(mDialogView)
            builder.setTitle(name)
            builder.setMessage(address)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
                val etext=mDialogView.findViewById<EditText>(R.id.commentBox)
                val rbar=mDialogView.findViewById<RatingBar>(R.id.rbar)
                mFirebaseDatabaseInstances?.getReference("restaurant")?.child("restaurant")?.setValue(
                    name+","+address+","+etext.text+","+rbar.numStars)

            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }


            builder.show()
            this.closeContextMenu()

//            val view: View =
//                layoutInflater.inflate(R.layout.info_window, null)
//
//
//            //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//         //   view.setCancelable(false)
//           val tvTitle = view.findViewById<TextView>(R.id.title)
//            val tvSnippet = view.findViewById<TextView>(R.id.snippet)

//            tvTitle.text =name
//            tvSnippet.text = address

            //   view.show()



        }
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
        //  mMap?.setOnCameraMoveListener(getAc)
        //  mMap?.setOnMapLoadedCallback(this)


    }

    override fun onMapLoaded() {
        TODO("Not yet implemented")
    }


}