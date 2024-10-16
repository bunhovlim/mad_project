package kh.edu.rupp.ite.mad_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Sign-up link click listener
        val signUpLink: TextView = findViewById(R.id.signUpLink)
        signUpLink.setOnClickListener {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        }

        // Login button click listener
        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
        // Placeholder for login action
        Toast.makeText(this, "Login Button Clicked", Toast.LENGTH_SHORT).show()
        // Here, you can add logic to handle login validation and authentication
                        }
                }
        }
