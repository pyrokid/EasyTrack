package com.easytrack.app.modules.main.reports.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.easytrack.app.R
import com.easytrack.app.data.model.Report
import com.easytrack.app.data.model.Report.Companion.convertLongToDate
import com.easytrack.app.modules.main.reports.datepickers.EditReportDatePicker
import com.easytrack.app.modules.main.reports.datepickers.EditReportTimePicker
import com.easytrack.app.modules.main.reports.view.StartOrFinishEditor.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class EditReportsDialog(
    private val reportEditor: IReportDialogEditor,
    private val report: Report
) : DialogFragment(), IDatePickerChanger, ITimePickerChanger {

    //region variables initiation
    lateinit var taskName: TextView
    lateinit var spentTime: TextView
    lateinit var startTimeValue: TextView
    lateinit var finishTimeValue: TextView
    lateinit var taskImage: CircleImageView
    lateinit var startDateValue: TextView
    lateinit var finishDateValue: TextView
    lateinit var confirmButton: Button
    lateinit var cancelButton: Button
    lateinit var deleteButton: ImageView
    //endregion

    var startDate: Long = report.periodStart
    var endDate: Long? = report.periodFinish

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setTitle("Title!")

        //region findViewById
        val v = inflater.inflate(R.layout.item_edit_report, null)

        taskName = v.findViewById(R.id.taskName)
        spentTime = v.findViewById(R.id.spentTime)
        startTimeValue = v.findViewById(R.id.startTimeValue)
        startDateValue = v.findViewById(R.id.startDateValue)
        finishTimeValue = v.findViewById(R.id.finishTimeValue)
        finishDateValue = v.findViewById(R.id.finishDateValue)
        taskImage = v.findViewById(R.id.taskImage)
        confirmButton = v.findViewById(R.id.confirmButton)
        cancelButton = v.findViewById(R.id.cancelButton)
        deleteButton = v.findViewById(R.id.deleteReportButton)
        //endregion

        updateReportInfo()

        startTimeValue.setOnClickListener {
            showDialog(EditReportTimePicker(this, STARTTIME, report.periodStart))
        }

        finishTimeValue.setOnClickListener {
            showDialog(EditReportTimePicker(this, FINISHTIME, report.periodFinish as Long))
        }

        startDateValue.setOnClickListener {
            showDialog(EditReportDatePicker(this, STARTDATE, report.periodStart))
        }

        finishDateValue.setOnClickListener {
            showDialog(EditReportDatePicker(this, FINISHDATE, report.periodFinish as Long))
        }


        deleteButton.setOnClickListener {
            reportEditor.deleteReportById(report.id)
            dialog!!.dismiss()
        }

        confirmButton.setOnClickListener {
            if ((endDate!! - startDate) > 0L) {
                reportEditor.updateStartPeriod(report.id, convertLongToDate(startDate))
                reportEditor.updateFinishPeriod(report.id, convertLongToDate(endDate as Long))
                dialog!!.dismiss()
            }
            else Toast.makeText(activity, "Start date can't exceed end date", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return v
    }


    private fun showDialog(dialogFragment: DialogFragment) {
        if (null == fragmentManager?.findFragmentByTag(dialogFragment::class.java.name)) {
            fragmentManager!!.beginTransaction().apply {
                dialogFragment.setTargetFragment(this@EditReportsDialog, 0)
                add(dialogFragment, dialogFragment::class.java.name)
            }.commit()
        }
    }

    private fun updateReportInfo() {
        taskName.text = report.taskName

        spentTime.text = report.calculateSpentTime(startDate, endDate)
        finishTimeValue.text = Report.convertLongToStringTime(endDate as Long)
        finishDateValue.text = Report.convertLongToStringDate(endDate as Long)
        startTimeValue.text = Report.convertLongToStringTime(startDate)
        startDateValue.text = Report.convertLongToStringDate(startDate)

        Picasso.get().load(Uri.parse(report.taskImage))
            .error(R.drawable.ic_add_image)
            .into(taskImage)
    }

    override fun updateStartDate(date: Date) {
        startDate = date.time
        updateReportInfo()
    }

    override fun updateFinishDate(date: Date) {
        endDate = date.time
        updateReportInfo()
    }

    override fun updateStartTime(date: Date) {
        startDate = date.time
        updateReportInfo()
    }

    override fun updateFinishTime(date: Date) {
        endDate = date.time
        updateReportInfo()
    }
}

interface IDatePickerChanger {
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
}

interface ITimePickerChanger {
    fun updateStartTime(date: Date)
    fun updateFinishTime(date: Date)
}

enum class StartOrFinishEditor {
    STARTTIME, FINISHTIME, STARTDATE, FINISHDATE
}