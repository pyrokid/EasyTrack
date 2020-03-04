package com.easytrack.app.modules.main.management.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.easytrack.app.R
import com.easytrack.app.data.model.Task
import com.easytrack.app.modules.main.management.view.ManagementActionName.EDIT
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView

class ManageTaskDialog(
    private val actionName: ManagementActionName,
    private val iManageTask: IManageTask
) : DialogFragment() {


    var task: Task = Task(0, "", "", true)
    var photo: String = ""
    private lateinit var editPhotoImgView: CircleImageView

    constructor(
        actionName: ManagementActionName,
        customDI: IManageTask,
        _task: Task
    ) : this(
        actionName,
        customDI
    ) {
        task = _task
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setTitle("Title!")

        //region findViewById
        val v = inflater.inflate(R.layout.item_add_task, null)
        val confirmButton: Button = v.findViewById(R.id.confirmAddTask)
        val cancelButton: Button = v.findViewById(R.id.cancelAddTask)
        val taskNameEditor: EditText = v.findViewById(R.id.editTaskName)
        val deleteTaskButton: Button = v.findViewById(R.id.deleteTaskButton)
        editPhotoImgView = v.findViewById(R.id.editImage)
        //endregion

        Picasso.get().load(Uri.parse(task.taskImage))
            .error(R.drawable.ic_add_image)
            .into(editPhotoImgView)

        if (actionName == EDIT)
            deleteTaskButton.visibility = View.VISIBLE
        taskNameEditor.setText(task.taskName)
        taskNameEditor.setSelection(task.taskName.length)

        confirmButton.setOnClickListener {
            if (taskNameEditor.text.toString() != "") {
                if (actionName == EDIT)
                    iManageTask.changeTask(task.id, taskNameEditor.text.toString(), photo)
                else
                    iManageTask.addTask(task.id, taskNameEditor.text.toString(), photo)
                dialog!!.dismiss()
            } else
                Toast.makeText(this.activity, "Task name can't be null", Toast.LENGTH_SHORT).show()

        }

        editPhotoImgView.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
                .setFixAspectRatio(true).start(this.activity!!, this)
        }

        cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        deleteTaskButton.setOnClickListener {
            iManageTask.deleteTask(task.id)
            dialog!!.dismiss()
        }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                Picasso.get().load(resultUri).into(editPhotoImgView)
                photo = resultUri.toString()
            }
        }
    }
}