package com.dzul.apps.subs3aplikasigithubuserdzulfikar.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.MainActivity
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        // Id untuk Reminder
        private const val ID_REMINDER = 11
        private const val TIME_FORMAT = "HH:mm"

    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val message = context.getString(R.string.notif_message)
        val title = context.getString(R.string.notif_title)
        val notifId = ID_REMINDER


        showToast(context,title, message)

        showReminderNotification(context, title, message, notifId)
    }

    private fun showToast(context: Context, title: String, message: String?){
        Toast.makeText(context, "$title: $message", Toast.LENGTH_SHORT).show()
    }

    private fun showReminderNotification(context: Context, title: String, message: String, notifId: Int){
        val CHANNEL_ID = "channel_1"
        val CHANNEL_NAME = "reminder_channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val reminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val homeIntent = Intent(context, MainActivity::class.java)
        val pendingHomeIntent = PendingIntent.getActivity(context, 0, homeIntent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingHomeIntent)
            .setSmallIcon(R.drawable.ic_access_time_black)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(reminderSound)

        // Menambahkan Notification Channel untuk Android ORIO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingNotification(context: Context, time: String){
        Log.d("notif", "called")
        if(isTimeInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    //
    fun cancelReminder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val requestCode = ID_REMINDER
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
    }

    private fun isTimeInvalid(time: String, format: String): Boolean{
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        } catch (e: ParseException){
            true
        }
    }

}
