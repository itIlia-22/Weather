package com.example.move

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.move.history.historyviewmodel.view.HistoryFragment
import com.example.move.view.WeatherCityListFragment
import com.example.move.view.map.MapsFragment

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherCityListFragment.newInstance())
                .commitNow()
        }

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.historyMenu -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }


            }

            R.id.menu_google_maps -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, MapsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()

                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

}