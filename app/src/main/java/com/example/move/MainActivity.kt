package com.example.move

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.move.history.historyviewmodel.view.HistoryFragment
import com.example.move.view.WeatherCityListFragment

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
        return when (item.itemId) {
            R.id.historyMenu -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}