package com.projemanag.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.projemanag.databinding.ActivitySplashBinding
import com.projemanag.firebase.FirestoreClass

// TODO (Step 1: Add new activity named SplashActivity.)
// START
class SplashActivity : AppCompatActivity() {

    // TODO (Step 2: Declare binding variable for ViewBinding)
    // This is used to access all views in activity_splash.xml
    private lateinit var binding: ActivitySplashBinding

    /**
     * This function is called when the SplashActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO (Step 3: Call the superclass constructor)
        super.onCreate(savedInstanceState)

        // TODO (Step 4: Initialize binding using ViewBinding)
        // It inflates the layout and gives access to its views.
        binding = ActivitySplashBinding.inflate(layoutInflater)

        // TODO (Step 5: Set the content view to the root of binding)
        setContentView(binding.root)

        // TODO (Step 6: Make splash screen full screen by hiding the status bar)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // TODO (Step 7: Set custom font from assets to the app name TextView)
        // 1. Add the font file (e.g., carbon bl.ttf) to the assets folder
        // 2. This code applies that custom font to the TextView
        val typeface: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")

        // Apply the font to the TextView using ViewBinding
        binding.tvAppName.typeface = typeface
        // TODO (Step 9: Here we will launch the Intro Screen after the splash screen using the handler. As using handler the splash screen will disappear after what we give to the handler.)
        // Adding the handler to after the a task after some delay.
        Handler().postDelayed({

            var currentUserID=FirestoreClass().getCurrentUserID()
            if(currentUserID.isNotEmpty()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            else {
                // Start the Intro Activity
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            }
            finish() // Call this when your activity is done and should be closed.
        }, 2500) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.
    }
}
// END
