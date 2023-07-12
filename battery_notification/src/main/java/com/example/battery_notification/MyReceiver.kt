package com.example.battery_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        val builder : NotificationCompat.Builder

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "one-channel"
            val chaneelName = "My Chaneel One"
            val channel = NotificationChannel(
                channelId,
                chaneelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                description = "My Chaneel One Description"
                setShowBadge(true)
                val uri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(context, channelId)
        } else{
            builder = NotificationCompat.Builder(context)
        }

        builder.run {
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setWhen(System.currentTimeMillis())
            setContentTitle("박성훈")
            setContentText("ㅎㅇ")
        }
        manager.notify(11, builder.build())
    }
}