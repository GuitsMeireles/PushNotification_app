package com.testenotificao

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage).toString()
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        handlerShowNotification(applicationContext, title, body)
    }

    private fun handlerShowNotification(context: Context, title: String?, message: String?) {

        val title = HtmlCompat.fromHtml(
            "<strong> $title </strong>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        val builder: NotificationCompat.Builder

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = Random.nextInt(1, 100)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        builder = NotificationCompat.Builder(this)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.baseline_email_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        notificationManager.notify(id, builder.build())
    }
}