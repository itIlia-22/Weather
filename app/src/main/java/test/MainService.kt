package test

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainService(name: String = "MainService") : IntentService(name) {





    //Отправка уведомления о завершении сервиса
    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        //createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }

    }

    private fun createLogMessage(message: String) {
        Log.e(TAG, message)
    }


    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }

    @Deprecated("Deprecated in Java")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


}