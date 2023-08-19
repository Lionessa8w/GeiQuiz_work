package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var previousButton: Button

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
        previousButton= findViewById(R.id.previous_button)



        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)

        }
        nextButton.setOnClickListener {
            currentIndex=(currentIndex+1)%questionBank.size
            updateQuestion()
        }
        previousButton.setOnClickListener {
            currentIndex = if (currentIndex in 1 until  questionBank.size){
                (currentIndex-1)%questionBank.size
            }else{
                questionBank.size-1
            }
            updateQuestion()
        }
        updateQuestion()
    }
    private fun updateQuestion(){
        val questionTextResId=questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(answer:Boolean){
        val correctAnswer=questionBank[currentIndex].answer
        val message = if (answer==correctAnswer){
            "Correct!"
        }else{
            "Incorrect!"
        }
        val toast=Toast.makeText(this,message,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0,0)
        toast.show()
    }
}