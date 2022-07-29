package com.example.partyernewversion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.Animation.Type.SMOOTH
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity(), UserLocationObjectListener, CameraListener, InputListener {

    private val requestPermissionLocation = 1
    private val MAPKIT_API_KEY = "c4e25bdd-cf32-46b8-bf87-9c547fa9b989"
    private var mapView: MapView? = null
    private lateinit var userLocationLayer: UserLocationLayer
    private var routeStartLocation = Point(0.0, 0.0)
    private var pointZoom: Point? = null
    private var zoom = 0f

    private var permissionLocation = false
    private var followUserLocation = false

    private lateinit var userLocationButton:ImageButton
    private lateinit var buttonAddPlaceMark:ImageButton
    lateinit var buttonProfileUser:ImageButton

    private var latitudeTapPoint:Double = 0.0
    private var longitudeTapPoint:Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        mapView = findViewById<View>(R.id.mapview) as MapView
        mapView!!.map.addInputListener(this)
        userLocationButton = findViewById(R.id.myLocation)
        buttonAddPlaceMark = findViewById(R.id.buttonAddPlaceMark)
        buttonProfileUser = findViewById(R.id.buttonProfileUser)

        checkPermission()
        userInterface()

        val minusZoomButton = findViewById<ImageButton>(R.id.minusZoom)
        val plusZoomButton = findViewById<ImageButton>(R.id.plusZoom)

        minusZoomButton.setOnClickListener {
            mapView!!.map.move(
                CameraPosition(pointZoom!!, zoom - 2, 0.0f, 0.0f),
                Animation(SMOOTH, 1F),
                null
            )
        }

        plusZoomButton.setOnClickListener {
            mapView!!.map.move(
                CameraPosition(pointZoom!!, zoom + 2, 0.0f, 0.0f),
                Animation(SMOOTH, 1F),
                null
            )
        }
        buttonAddPlaceMark.setOnClickListener {
            bottomSheetCreate(false)
        }
        buttonProfileUser.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfilCurrentUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkPermission() {
        val permissionLocation =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissionLocation
            )
        } else {
            onMapReady()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestPermissionLocation -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady()
                }

                return
            }
        }
    }

    private fun userInterface() {
        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        mapView!!.map.logo.setAlignment(mapLogoAlignment)

        userLocationButton.setOnClickListener {
            if (permissionLocation) {
                cameraUserPosition()

                followUserLocation = true
            } else {
                checkPermission()
            }
        }
    }

    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView!!.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)

        mapView!!.map.addCameraListener(this)

        cameraUserPosition()

        permissionLocation = true
    }

    private fun cameraUserPosition() {
        if (userLocationLayer.cameraPosition() != null) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            mapView!!.map.move(
                CameraPosition(routeStartLocation, 16f, 0f, 0f), Animation(SMOOTH, 1f), null
            )
        } else {
            mapView!!.map.move(CameraPosition(Point(0.0, 0.0), 16f, 0f, 0f))
        }
    }

    override fun onStop() {
        mapView!!.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView!!.onStart()
    }

    override fun onObjectAdded(p0: UserLocationView) {
        setAnchor()

        p0.pin.setIcon(ImageProvider.fromResource(this, R.drawable.user_arrow))
        p0.arrow.setIcon(ImageProvider.fromResource(this, R.drawable.user_arrow))
        p0.accuracyCircle.fillColor = Color.BLUE
    }

    override fun onObjectRemoved(p0: UserLocationView) { }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) { }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {
        if (p3) {
            if (followUserLocation) {
                setAnchor()
            }
        } else {
            if (!followUserLocation) {
                noAnchor()
            }
        }
        pointZoom = Point(
            p1.target.latitude,
            p1.target.longitude
        )
        zoom = p1.zoom
    }
    private fun setAnchor() {
        userLocationLayer.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )
        followUserLocation = false
    }

    private fun noAnchor() {
        userLocationLayer.resetAnchor()
    }

    fun bottomSheetCreate(isMapTapPoint: Boolean){
        val bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )
        val bottonSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.layout_bottom_sheet,
            findViewById<LinearLayout>(R.id.bottomSheetContainer)
        )
        bottomSheetDialog.setContentView(bottonSheetView)
        bottomSheetDialog.show()

        val buttonYes = bottomSheetDialog.findViewById<Button>(R.id.buttonYes)
        val buttonCancelAddPlaceMark =
            bottomSheetDialog.findViewById<Button>(R.id.buttonСancelAddPlaceMark)

        buttonYes?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddPlaceMarkActivity::class.java)
            intent.putExtra("isMapTapPoint", isMapTapPoint)
            if(isMapTapPoint) {
                intent.putExtra("userLatitude", userLocationLayer.cameraPosition()!!.target.latitude)
                intent.putExtra("userLongitude", userLocationLayer.cameraPosition()!!.target.longitude)
                intent.putExtra("latitudeTapPoint", latitudeTapPoint)
                intent.putExtra("longitudeTapPoint", longitudeTapPoint)
            } else {
                intent.putExtra("userLatitude", userLocationLayer.cameraPosition()!!.target.latitude)
                intent.putExtra("userLongitude", userLocationLayer.cameraPosition()!!.target.longitude)
            }


            startActivity(intent)
        }
        buttonCancelAddPlaceMark?.setOnClickListener {
            bottomSheetDialog.hide()
        }
    }
    override fun onMapTap(p0: Map, p1: Point) {
        Log.d("MAP_TAG", "point: " + p1.latitude + ", " + p1.longitude)

        latitudeTapPoint = p1.latitude
        longitudeTapPoint = p1.longitude
        bottomSheetCreate(true)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {

    }

}

