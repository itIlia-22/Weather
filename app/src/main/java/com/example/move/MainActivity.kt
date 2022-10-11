package com.example.move

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.move.view.WeatherCityListFragment
import test.MainBroadcastReceiver
import test.TestThreadFragment

class MainActivity : AppCompatActivity() {
    private val receiver = MainBroadcastReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


          if (savedInstanceState == null){

              supportFragmentManager.beginTransaction()
                  .replace(R.id.container,WeatherCityListFragment.newInstance()).addToBackStack("").commit()
          }
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test_thread, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, TestThreadFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}