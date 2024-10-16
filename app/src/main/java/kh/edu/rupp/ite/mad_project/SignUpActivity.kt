package kh.edu.rupp.ite.mad_project

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
        finish() // Go back to the previous activity (LoginActivity)
                    }
                }
        }
