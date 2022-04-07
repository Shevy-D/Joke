@file:Suppress("DEPRECATION")

package com.shevy.joke

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat

@Suppress("DEPRECATION")
class DelayedMessageService : IntentService("DelayedMessageService") {
    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        synchronized(this) {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        val text = intent!!.getStringExtra(EXTRA_MESSAGE)
        showText(text)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showText(text: String?) {
        val builder = NotificationCompat.Builder(this, EXTRA_MESSAGE)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(this.resources.getString(R.string.question))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 1000))
            .setAutoCancel(true)

        //Create an action
        val actionIntent = Intent(this, MainActivity::class.java)
        val actionPendingIntent = PendingIntent.getActivity(
            this,
            0,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(actionPendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        const val EXTRA_MESSAGE = "message"
        const val NOTIFICATION_ID = 5453
    }
}