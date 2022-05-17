package com.example.quizboxapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    private lateinit var correctNum : TextView
    private lateinit var nextBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

//        val evaluate = findViewById<TextView>(R.id.evaluate)
//        val wish = findViewById<TextView>(R.id.wish)
//        val emotion = findViewById<ImageView>(R.id.emotion)
//        val score : Int = intent.getStringExtra("Score").toString().toInt()
        correctNum = findViewById(R.id.correct_num)
        nextBtn = findViewById(R.id.nl_btn)

        //custom lời chúc, nhận xét, nhạc
//        if(score <= 10) {
//            evaluate.text = "Poor!"
//            emotion.setImageResource(R.drawable.sad_img)
//        }

        correctNum.text = intent.getStringExtra("CorrectNum")


        nextBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}