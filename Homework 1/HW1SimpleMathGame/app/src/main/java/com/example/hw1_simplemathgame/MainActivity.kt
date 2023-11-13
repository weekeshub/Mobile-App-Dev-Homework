package com.example.hw1_simplemathgame

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.util.Random

class MainActivity : AppCompatActivity() {

    var numCorrect = 0
    var numQuestions = 0
    val randomNumberBound = 100
    val incorrectInput = "You must enter an integer value."
    var num1 = 0
    var num2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize(){
        generateLargerFirstNumber()
        answerInput.text.clear()
    }

    private fun generateRandomNumber(bound: Int): Int {
        var randomNumber = Random().nextInt(randomNumberBound)
        if (randomNumber <= 89){
            randomNumber += 10
        }
        return randomNumber
    }

    private fun generateLargerFirstNumber(){
        num1 = generateRandomNumber(randomNumberBound)
        num2 = generateRandomNumber(randomNumberBound)
        while (num1 <= num2) {
            if (num1 == 10) {
                num1 = generateRandomNumber(randomNumberBound)
            }
            num2 = generateRandomNumber(randomNumberBound)
        }
        firstNumber.text = "$num1"
        secondNumber.text = "$num2"
    }

    fun submit(view: View){
        val answer = findViewById<EditText>(R.id.answerInput).text.toString().toIntOrNull()
        val correctAnswer = firstNumber.text.toString().toInt() - secondNumber.text.toString().toInt()
        var textView = findViewById<TextView>(R.id.feedback)

        if(answer == null) {
            Toast.makeText(this, "$incorrectInput", Toast.LENGTH_SHORT).show()
            return
        }

        if ((correctAnswer) == answer){
            numCorrect++
            numQuestions++
            feedback.text = "Correct!"
            textView.setTextColor(Color.parseColor("#00FF1A"))
            score.text = "Score: $numCorrect/$numQuestions"
            initialize()
        } else {
            numQuestions++
            feedback.text = "Incorrect. $num1 - $num2 = $correctAnswer"
            textView.setTextColor(Color.parseColor("#FF0000"))
            score.text = "Score: $numCorrect/$numQuestions"
            initialize()
        }
        answerInput.hideKeyboard()
    }

    private fun View.hideKeyboard(){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun reset(view: View){
        numCorrect = 0
        numQuestions = 0
        score.text = "Score: $numCorrect/$numQuestions"
        feedback.text = null
        initialize()
    }

}