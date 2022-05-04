package com.example.quizboxapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.airbnb.lottie.LottieAnimationView
import com.example.quizboxapp.user.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val emailCheck = preferences.getString("Email", "Not Email Exist")

        if(emailCheck!== "") {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        //Animations
        val startAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom)
        val splashTextAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left)
        val splashText1Animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_top)

        //Hooks

        //Hooks
        val startBtn = findViewById<Button>(R.id.start_btn)
        val txtSplashText = findViewById<TextView>(R.id.sp_tv)
        val txtSplashText1 = findViewById<TextView>(R.id.sp_tv1)
        val logoImageView = findViewById<ImageView>(R.id.sp_logo)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.Logloading)

        //Start Animation

        //Start Animation
        startBtn.animation = startAnimation
        logoImageView.animation = logoAnimation
        txtSplashText1.animation = splashText1Animation
        txtSplashText.animation = splashTextAnimation

        startBtn.setOnClickListener {
            lottieAnimationView.visibility
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}