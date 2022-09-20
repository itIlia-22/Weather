package com.example.move

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.move.view.WeatherCityListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,WeatherCityListFragment.newInstance()).addToBackStack("").commit()
        }
    }
}