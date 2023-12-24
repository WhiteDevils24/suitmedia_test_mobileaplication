package com.akbar.suitmediatestaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.akbar.suitmediatestaplication.data.User

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var nameOutput: TextView
    private lateinit var nameOutputSelectedUser: TextView
    private lateinit var btnChooseUser: Button


    val REQUEST_CODE_THIRD_SCREEN = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)


        // Set up the toolbar
        val toolbarFormData: Toolbar = findViewById(R.id.tb_secondScreen)
        setSupportActionBar(toolbarFormData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        nameOutput = findViewById(R.id.tv_nameOutput)
        nameOutputSelectedUser = findViewById(R.id.tv_nameOutputSelectedUser)
        btnChooseUser = findViewById(R.id.btn_ChooseUser)

        // Get the name from the intent and set it to the nameOutput TextView
        val name = intent.getStringExtra("name")
        nameOutput.text = name

        // Set up the "Choose a User" button click listener
        btnChooseUser.setOnClickListener {
            // Navigate to the Third Screen
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivity(intent)
        }

        val user: User? = intent.getParcelableExtra("user")

        if (user != null) {
            nameOutputSelectedUser.text = "${user.first_name} ${user.last_name}"
        }

        val btnChooseUser = findViewById<Button>(R.id.btn_ChooseUser)

        btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_THIRD_SCREEN)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}



