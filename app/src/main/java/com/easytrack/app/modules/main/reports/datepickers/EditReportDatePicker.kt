package com.easytrack.app.modules.main.reports.datepickers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.easytrack.app.R
import com.easytrack.app.data.model.Report
import com.easytrack.app.modules.main.reports.view.IDatePickerChanger
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor.FINISHDATE
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor.STARTDATE

class EditReportDatePicker(
    private val datePickerChanger: IDatePickerChanger,
    private val indicator: StartOrFinishEditor,
    private var date: Long
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setTitle("Title!")

        //region findviewbyid
        val v = inflater.inflate(R.layout.item_date_picker, null)
        val datePicker: DatePicker = v.findViewById(R.id.datePicker)
        val confirmButton: Button = v.findViewById(R.id.confirmButton)
        val cancelButton: Button = v.findViewById(R.id.cancelButton)
        //endregion

        val currentDate = Report.convertLongToDate(date)

        datePicker.init(
            currentDate.year + 1900,
            currentDate.month,
            currentDate.date
        ) { datePicker, _, _, _ ->
            currentDate.year = datePicker.year
            currentDate.month = datePicker.month
            currentDate.date = datePicker.dayOfMonth
        }

        confirmButton.setOnClickListener {
            currentDate.year = currentDate.year - 1900
            when (indicator) {
                STARTDATE -> datePickerChanger.updateStartDate(currentDate)
                FINISHDATE -> datePickerChanger.updateFinishDate(currentDate)
            }
            dialog!!.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return v
    }
}