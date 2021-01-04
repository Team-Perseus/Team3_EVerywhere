package com.example.everywhere

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOG_TAG = "MainActivity"
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf<String>(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var mNaverMap: NaverMap

    //private var mBackWait: Long = 0
    //private lateinit var mLayout: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_view, it).commit()
            }
        navi_button.setOnClickListener {
            layout_drawer.openDrawer(GravityCompat.START) // START:left, END:right 랑 같은 말
        }
            mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d(LOG_TAG, "onMapReady")

        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        mNaverMap = naverMap
        mNaverMap.locationSource = mLocationSource

        var uiSettings = mNaverMap.uiSettings
        uiSettings.isZoomControlEnabled = false
        uiSettings.isLocationButtonEnabled = true

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
    }

    override fun onBackPressed() { // 백버튼
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) { // 메뉴가 열려있을 때
            layout_drawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }


}