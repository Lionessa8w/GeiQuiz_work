package com.bignerdranch.android.geomain

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


private const val TAG = "MainActivity"
private const val KEI_INDEX = "index"
private const val REQUEST_CODE_CHAT = 0


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var quizViewModel: QuizViewModel

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)


        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        val currentIndex = savedInstanceState?.getInt(KEI_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        previousButton = findViewById(R.id.previous_button)

        trueButton.setOnClickListener {
            changeAnswer(true)

        }

        falseButton.setOnClickListener {
            changeAnswer(false)

        }
        nextButton.setOnClickListener {
            quizViewModel.moveToText()
            updateQuestion()
            enabledButton(true)
        }
        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHAT)
        }
        updateQuestion()

        previousButton.setOnClickListener {
            if (quizViewModel.currentIndex in 1 until quizViewModel.questionBank.size) {
                quizViewModel.currentIndex--
            } else {
                quizViewModel.currentIndex = quizViewModel.questionBank.size - 1
            }
            updateQuestion()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOW, false) ?: false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEI_INDEX, quizViewModel.currentIndex)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun changeAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        val currentQuestion: Int = quizViewModel.currentQuestionText
        if (quizViewModel.questionBank.map { it.textResId }.contains(currentQuestion)) {
            enabledButton(false)
            quizViewModel.questionBank.removeAt(quizViewModel.currentIndex)
        }
        if (userAnswer == quizViewModel.currentQuestionAnswer) {
            count++

        }
        if (quizViewModel.questionBank.size == 0) {
            val result1: Double = (count.toDouble() / 6)
            val result: Int = (result1 * 100).toInt()
            Toast.makeText(this, "Правильных ответов $result%", Toast.LENGTH_LONG).show()
        }

    }

    private fun updateQuestion() {
        Log.d(TAG, " update question exception", Exception())
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun enabledButton(enabledButton: Boolean) {
        trueButton.isEnabled = enabledButton
        falseButton.isEnabled = enabledButton

    }


}