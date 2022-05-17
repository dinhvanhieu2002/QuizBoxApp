package com.example.quizboxapp

import android.annotation.SuppressLint
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

        val score = intent.getIntExtra("Score", 0)
        val evaluate = findViewById<TextView>(R.id.evaluate)
        val emotion = findViewById<ImageView>(R.id.emotion)
        val wish = findViewById<TextView>(R.id.wish)

        correctNum = findViewById(R.id.correct_num)
        nextBtn = findViewById(R.id.nl_btn)

        //custom lời chúc, nhận xét, nhạc

        when {
            score <= 4 -> {
                evaluate.text = "Poor!"
                wish.text = "You need to try more "
                emotion.setImageResource(R.drawable.sad_img)
            }
            score <= 7 -> {
                evaluate.text = "Good!"
                wish.text = "You finished your test well but you need to practice more "
            }
            else -> {
                evaluate.text = "Excellent!"
                wish.text = "You completed your quiz excellent "
            }
        }


        correctNum.text = intent.getStringExtra("CorrectNum")


        nextBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    @SuppressLint("SetTextI18n")
//    private fun customWish(score : Int) {
//        when {
//            score <= 4 -> {
//                evaluate.text = "Poor!"
//                wish.text = "You need to try more "
//                emotion.setImageResource(R.drawable.sad_img)
//            }
//            score <= 7 -> {
//                evaluate.text = "Good!"
//                wish.text = "You finished your test well but you need to practice more "
//            }
//            else -> {
//                evaluate.text = "Excellent!"
//                wish.text = "You completed your quiz excellent "
//            }
//        }
//    }
}