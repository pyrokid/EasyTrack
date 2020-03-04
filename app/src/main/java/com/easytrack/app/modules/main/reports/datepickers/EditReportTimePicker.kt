package com.easytrack.app.modules.main.reports.datepickers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.easytrack.app.R
import com.easytrack.app.data.model.Report
import com.easytrack.app.modules.main.reports.view.ITimePickerChanger
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor.FINISHTIME
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor.STARTTIME

class EditReportTimePicker(
    private val timePickerChanger: ITimePickerChanger,
    private val indicator: StartOrFinishEditor,
    private val date: Long
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setTitle("Title!")

        // region findviewid
        val v = inflater.inflate(R.layout.item_time_picker, null)
        val timePicker: TimePicker = v.findViewById(R.id.timePicker)
        val confirmButton: Button = v.findViewById(R.id.confirmButton)
        val cancelButton: Button = v.findViewById(R.id.cancelButton)
        //endregion

        val currentTime = Report.convertLongToDate(date)

        timePicker.currentHour = currentTime.hours
        timePicker.currentMinute = currentTime.minutes

        timePicker.setIs24HourView(true)

        timePicker.setOnTimeChangedListener { timePicker, _, _ ->
            currentTime.hours = timePicker.currentHour
            currentTime.minutes = timePicker.currentMinute
        }

        confirmButton.setOnClickListener {
            when (indicator) {
                STARTTIME -> timePickerChanger.updateStartTime(currentTime)
                FINISHTIME -> timePickerChanger.updateFinishTime(currentTime)
            }
            dialog!!.dismiss()
        }

        cancelButton.setOnClickListener{
            dialog!!.dismiss()
        }

        return v
    }
}