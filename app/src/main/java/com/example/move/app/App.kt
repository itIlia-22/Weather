package com.example.move.app

import android.app.Application
import androidx.room.Room
import com.example.move.model.romm.HistoryDao
import com.example.move.model.romm.HistoryDataBase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this

    }

    companion object {
         var appInstance: App? = null
         var db: HistoryDataBase? = null


        @OptIn(InternalCoroutinesApi::class)
        fun getHistoryDao(): HistoryDao {
            synchronized(HistoryDataBase::class.java){
                if (db == null) {
                    if (null != appInstance) {
                        db = Room.databaseBuilder(appInstance!!.applicationContext, HistoryDataBase::class.java, "1")
                            .allowMainThreadQueries()
                            .build()

                    }

                }
            }

            return db!!.historyDao()
        }
    }
}