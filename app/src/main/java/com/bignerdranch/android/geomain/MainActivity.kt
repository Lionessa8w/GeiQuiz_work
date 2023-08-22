package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton:Button
    private lateinit var questionTextView: TextView

    private val questionBank= listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true)
    )
    private var currentIndex=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton=findViewById(R.id.next_button)
        questionTextView= findViewById(R.id.question_text_view)
        previousButton=findViewById(R.id.previous_button)



        trueButton.setOnClickListener {
            changeAnswer(true)
        }

        falseButton.setOnClickListener {
            changeAnswer(false)

        }
        nextButton.setOnClickListener {
            currentIndex=(currentIndex+1)%questionBank.size
            updateQuestion()
        }
        updateQuestion()

        previousButton.setOnClickListener {
            if (currentIndex in 1 until questionBank.size){
                currentIndex--
            }else{
                currentIndex=questionBank.size-1
            }
            updateQuestion()
        }

    }
    private fun changeAnswer(userAnswer:Boolean){
        val currentAnswer=questionBank[currentIndex].answer
        val message = if (userAnswer==currentAnswer){
            "Correct!"
        }else{
            "Incorrect!"
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

    }
    private fun updateQuestion(){
        val questionTextResId=questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }


}