package com.dzul.apps.subs3aplikasigithubuserdzulfikar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.PreferencesItem
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.fragment.TimePickerFragment
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.preference.AppPreferences
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.receiver.ReminderReceiver
import kotlinx.android.synthetic.main.activity_settings.*
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener, TimePickerFragment.DialogTimeListener {

    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var preferencesModel: PreferencesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = getString(R.string.option_setting)

        settings_enablereminder.setOnClickListener(this)
        settings_imgclock.setOnClickListener(this)
        settings_disablereminder.setOnClickListener(this)

        reminderReceiver = ReminderReceiver()
        preferencesModel = intent?.getParcelableExtra<PreferencesItem>("PREFS") as PreferencesItem
        Log.d("AppPrefs: ", preferencesModel.toString())
        getPreferences()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.settings_imgclock -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "REPEATING")
            }

            R.id.settings_enablereminder -> {
                if(settings_timetitle.text.equals(getString(R.string.settings_txt_alarm))){
                    Toast.makeText(this, "Time Not Set", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    val repeatTime = settings_timetitle.text.toString()
                    Toast.makeText(this, repeatTime, Toast.LENGTH_SHORT).show()
                    reminderReceiver.setRepeatingNotification(this, repeatTime)
                    savePreferences()
                    getPreferences()
                }
            }

            R.id.settings_disablereminder -> {
                disableReminder()
            }
        }
    }

    override fun onDialogTimeSet(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        settings_timetitle.text = dateFormat.format(calendar.time)
        Log.d("Format", dateFormat.format(calendar.time))
    }

    private fun savePreferences(){
        val appPrefs = AppPreferences(this)

        preferencesModel.time = settings_timetitle.text.toString()
        preferencesModel.isSet = true

        appPrefs.setPreferences(preferencesModel)

    }

    private fun getPreferences(){
        if(preferencesModel.time != null && preferencesModel.isSet != false){
            settings_timetitle.text = preferencesModel.time
            settings_enablereminder.visibility = View.GONE
            settings_disablereminder.visibility = View.VISIBLE
        } else {
            settings_timetitle.text = getString(R.string.settings_txt_alarm)
        }
    }

    private fun disableReminder(){
        val appPrefs = AppPreferences(this)

        preferencesModel.time = null
        preferencesModel.isSet = false

        appPrefs.setPreferences(preferencesModel)

        settings_timetitle.text = getString(R.string.settings_txt_reminder)
        settings_disablereminder.visibility = View.GONE
        settings_enablereminder.visibility = View.VISIBLE

        reminderReceiver.cancelReminder(this)
    }
}