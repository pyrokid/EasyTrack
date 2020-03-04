package com.easytrack.app.modules.main.home.view

import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.easytrack.app.R
import com.easytrack.app.app.EasyTrackApplication.Companion.CURRENT_REPORT_ID
import com.easytrack.app.databinding.ActivityMainBinding
import com.easytrack.app.modules.common.view.BaseActivity
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.modules.main.home.PagerAdapter
import com.easytrack.app.modules.main.home.view.StartBtnState.*
import com.easytrack.app.modules.main.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import java.util.*


class HomeActivity : BaseActivity<EmptyInitialDataProvider, HomeViewModel, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<out ViewModel> = HomeViewModel::class.java

    override fun getInitialDataProvider() = EmptyInitialDataProvider

    var currentTaskId: Long? = null
    var startButtonState = HIDDEN

    override fun setupUI() {
        super.setupUI()
        //region ViewPager
        val adapter = PagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
        binding.pager.adapter = adapter
        binding.pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pager.currentItem = tab.position
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })
        //endregion

        binding.buttonPause.setOnClickListener {
            if (CURRENT_REPORT_ID != null) {
                viewModel.updateStopTracking(
                    CURRENT_REPORT_ID as Long,
                    Calendar.getInstance().time.time
                )
                CURRENT_REPORT_ID = null
                binding.buttonStart.setBackgroundResource(R.drawable.ic_play_arrow_green_50dp)
                startButtonState = START
            }
            else Toast.makeText(this, "You shouldn't pause tracking before start", Toast.LENGTH_SHORT).show()
        }

        binding.buttonStart.setOnClickListener {
            if (currentTaskId != null) {
                when (startButtonState) {
                    START -> {
                        viewModel.getTaskByID(currentTaskId!!).observe(this, Observer {
                            viewModel.createReport(
                                currentTaskId!!,
                                Calendar.getInstance().time.time,
                                it
                            )
                        })
                        binding.buttonStart.setBackgroundResource(R.drawable.stop_button_red_50dp)
                        startButtonState = STOP
                    }
                    STOP -> {
                        viewModel.updateStopTracking(
                            CURRENT_REPORT_ID as Long,
                            Calendar.getInstance().time.time
                        )
                        binding.buttonStart.setBackgroundResource(R.drawable.ic_play_arrow_green_50dp)
                        removeTaskFromPanel()
                        startButtonState = START
                    }
                    HIDDEN -> removeTaskFromPanel()
                }
            }
        }
    }

    fun removeTaskFromPanel() {
        CURRENT_REPORT_ID = null
        Picasso.get().load(R.drawable.ic_add_image).error(R.drawable.ic_add_task_circle_100dp)
            .into(binding.taskPanelImage)
        binding.buttonPause.visibility = View.GONE
        binding.buttonStart.visibility = View.GONE
        binding.taskPanelName.text = getString(R.string.SelectTaskText)
    }

    fun sendTaskToTrackPanel(id: Long) {
        currentTaskId = id
        viewModel.getTaskByID(id).observe(this, Observer { task ->
            startButtonState = START
            binding.taskPanelName.text = task.taskName
            binding.buttonPause.visibility = View.VISIBLE
            binding.buttonStart.visibility = View.VISIBLE
            Picasso.get().load(Uri.parse(task.taskImage)).error(R.drawable.ic_add_image)
                .into(binding.taskPanelImage)
        })
    }
}

enum class StartBtnState {
    START, STOP, HIDDEN
}