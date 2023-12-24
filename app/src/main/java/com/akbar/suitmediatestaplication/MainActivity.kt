package com.akbar.suitmediatestaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkButton = findViewById<Button>(R.id.btn_check)
        val nextButton = findViewById<Button>(R.id.btn_next)

        checkButton.setOnClickListener{
            checkPalindrome()
        }

        nextButton.setOnClickListener{
            toNextScreen()
        }

    }

    //Palindrome check Function
    private fun checkPalindrome() {
        val palindromeEditText = findViewById<EditText>(R.id.et_palindromeInput)
        
        val sentence = palindromeEditText.text.toString()

        val isPalindrome = isPalindrome(sentence)

        val message = if (isPalindrome) {
            "isPalindrome"
        } else {
            "not palindrome"
        }

        showAlertDialog(message)
    }
    //Palindrome function
    private fun isPalindrome(input: String): Boolean {
        val cleanedInput = input.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "")
        return cleanedInput == cleanedInput.reversed()
    }
    //Show Dialog
    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Palindrome Check")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ -> }
        builder.show()
    }

    //Intent to SecondScreen
    //With parsing name edit text to text view output in second screen
    private fun toNextScreen() {
        val nameEditText = findViewById<EditText>(R.id.et_nameInput)
        val nameOutput = nameEditText.text.toString()

        val intent = Intent(this, SecondScreenActivity::class.java)
        intent.putExtra("name",nameOutput)
        startActivity(intent)
    }


}