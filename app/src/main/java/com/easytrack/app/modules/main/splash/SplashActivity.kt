package com.easytrack.app.modules.main.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.easytrack.app.modules.main.home.view.HomeActivity

class SplashActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainIntent = Intent(this, HomeActivity::class.java)
        this.startActivity(mainIntent)
        this.finish()
    }
}