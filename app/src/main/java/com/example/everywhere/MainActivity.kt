package com.example.everywhere

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class MainActivity : AppCompatActivity(),
    OnMapReadyCallback {
    private val LOG_TAG = "MainActivity"
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf<String>(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var mNaverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //네이버 지도 생성 코드
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_view, it).commit()
            }
        mapFragment.getMapAsync(this)

        //검색 버튼 코드
        search_view.setOnClickListener {
            var searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        Timber.plant(Timber.DebugTree())
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d(LOG_TAG, "onMapReady")

        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        mNaverMap = naverMap
        mNaverMap.locationSource = mLocationSource

        var uiSettings = mNaverMap.uiSettings
        uiSettings.isZoomControlEnabled = false
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isCompassEnabled = false

        compass.map = naverMap

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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