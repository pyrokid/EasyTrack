package com.easytrack.app.utils.ui

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.easytrack.app.R
import com.squareup.picasso.Picasso.get

@BindingAdapter("taskStrToBtmp")
fun uploadImage(view: ImageView, taskImage: String) {
    get().load(Uri.parse(taskImage)).resize(200, 200).error(R.drawable.ic_add_image).into(view)
}

@BindingAdapter("visibility")
fun setProgressBarVisibility(view: ProgressBar, loading: MutableLiveData<Boolean>) {
    when (loading.value) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.GONE
    }
}