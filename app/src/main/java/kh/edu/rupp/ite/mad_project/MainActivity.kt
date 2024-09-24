package kh.edu.rupp.ite.mad_project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var loginTitleTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the TextView by its ID
        loginTitleTextView = findViewById(R.id.loginTitle)

        // Set the text to "Hello, My name is Molika."
        loginTitleTextView.text = "Hello, My name is Molika."
    }
}
