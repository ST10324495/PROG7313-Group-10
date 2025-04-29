package vcmsa.projects.prog7313_poe_group_10

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.prog7313_poe_group_10.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences for storing user data
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        // Check if user is already logged in
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        if (savedEmail?.isNotEmpty() == true && savedPassword?.isNotEmpty() == true) {
            binding.emailInput.setText(savedEmail)
            binding.passwordInput.setText(savedPassword)
        }

        // Set up login button click listener
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (validateInput(email, password)) {
                // Store credentials
                saveUserData(email, password)

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // Create intent to pass data to the next activity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
                finish() // Optional: close the login activity
            }
        }

        // Set up forgot password click listener
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show()
            // Implement password recovery flow here
        }

        // Set up create account click listener
        binding.createAccount.setOnClickListener {
            Toast.makeText(this, "Create account clicked", Toast.LENGTH_SHORT).show()
            // Navigate to sign up page
            /** val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent) */
        }
    }

    /**
     * Validates the email and password inputs
     * @param email The user's email address
     * @param password The user's password
     * @return True if both inputs are valid, false otherwise
     */
    private fun validateInput(email: String, password: String): Boolean {
        // Check if email and password are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate email format using Android's Patterns class
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate password length (optional: add more password strength requirements)
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    /**
     * Saves the user's email and password to SharedPreferences
     * @param email The user's email address
     * @param password The user's password
     */
    private fun saveUserData(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }
}