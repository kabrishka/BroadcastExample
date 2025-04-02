package com.kabrishka.broadcastexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kabrishka.broadcastexample.MainActivity.Companion.ACTION_LOADED
import com.kabrishka.broadcastexample.MainActivity.Companion.PERCENT_LOADED
import kotlin.concurrent.thread

class LoadingService : Service() {
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                Intent(ACTION_LOADED).apply {
                    putExtra(PERCENT_LOADED, i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}