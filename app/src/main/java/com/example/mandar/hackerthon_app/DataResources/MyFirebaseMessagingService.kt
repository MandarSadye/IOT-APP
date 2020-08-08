package com.example.mandar.hackerthon_app.DataResources

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.example.mandar.hackerthon_app.Activities.BaseActivity
import com.example.mandar.hackerthon_app.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by mandar on 29-12-2017.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        var indent = Intent(this,BaseActivity::class.java)
        indent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent = PendingIntent.getActivity(this,0,indent,PendingIntent.FLAG_ONE_SHOT)
        var notificationBuilder = NotificationCompat.Builder(this)
        notificationBuilder.setContentTitle("FCM NOTIFICATION")
        notificationBuilder.setContentText(p0!!.notification!!.body)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        notificationBuilder.setContentIntent(pendingIntent)
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0,notificationBuilder.build())
    }
}