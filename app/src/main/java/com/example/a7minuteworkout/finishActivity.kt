package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class finishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            finish()
        }

        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {

        val c = Calendar.getInstance() // Calendars Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.

        /**
         * Here we have taken an instance of Date Formatter as it will format our
         * selected date in the format which we pass it as an parameter and Locale.
         * Here I have passed the format as dd MMM yyyy HH:mm:ss.
         *
         * The Locale : Gets the current value of the default locale for this instance
         * of the Java Virtual Machine.
         */
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date)) // Add date function is called.
            Log.e(
                "Date : ",
                "Added..."
            ) // Printed in log which is printed if the complete execution is done.
        }
    }
}