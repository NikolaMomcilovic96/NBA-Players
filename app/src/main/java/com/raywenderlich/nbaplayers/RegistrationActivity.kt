package com.raywenderlich.nbaplayers

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.ActivityRegistrationBinding
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(Constants.sharedPref, Context.MODE_PRIVATE)

        val registrationButton = binding.registrationButton
        registrationButton.isEnabled = false

        val londonZone = ZoneId.of("Europe/London")
        val currentDate = ZonedDateTime.now(londonZone)
        binding.birthdatTextView.text =
            currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        val maleCheck = binding.maleCheckBox
        val femaleCheck = binding.femaleCheckBox
        var gender = ""
        var isMale = false
        var isFemale = false

        binding.datePickerButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.birthdatTextView.text = "$dayOfMonth-$monthOfYear-$year"
                }, year, month, day)
            dpd.show()
        }

        maleCheck.setOnClickListener {
            if (!isMale) {
                isMale = true
                isFemale = false
                gender = "Male"
                femaleCheck.isChecked = false
                registrationButton.isEnabled = true
            } else {
                isMale = false
                gender = ""
                maleCheck.isChecked = false
            }
        }

        femaleCheck.setOnClickListener {
            if (!isFemale) {
                isFemale = true
                isMale = false
                gender = "Female"
                maleCheck.isChecked = false
                registrationButton.isEnabled = true
            } else {
                isFemale = false
                gender = ""
                femaleCheck.isChecked = false
            }
        }

        registrationButton.setOnClickListener {
            inputCheck(gender)
        }

        val wikipediaLink = binding.privacyPolicyLink
        wikipediaLink.movementMethod = LinkMovementMethod.getInstance()
        wikipediaLink.setLinkTextColor(Color.BLUE)

        binding.backToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun inputCheck(gender: String) {
        val firstName = binding.firstNameInput.text.toString()
        val lastName = binding.lastNameInput.text.toString()
        val username = binding.usernameInput.text.toString()
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val passwordConfirm = binding.passwordConfirmationInput.text.toString()
        val birthday = binding.birthdatTextView.text.toString()
        firstName.trim()
        lastName.trim()
        username.trim()
        email.trim()
        password.trim()
        passwordConfirm.trim()
        birthday.trim()

        when {
            firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()
                    || passwordConfirm.isEmpty() || birthday.isEmpty() || gender.isEmpty() ->
                toastMessage("All fields are required")
            !nameCheck(firstName) -> toastMessage("Firstname format")
            !nameCheck(lastName) -> toastMessage("Lastname format")
            username.length < 4 -> toastMessage("Username short")
            !emailCheck(email) -> toastMessage("Email format")
            password != passwordConfirm -> toastMessage("Passwords doesn't match")
            !passwordFormatCheck(password) -> toastMessage("Password format")
            !birthdayCheck(birthday) -> toastMessage("Birthday format")
            else -> {
                sharedPreferences.edit().apply {
                    putString(Constants.USERNAME, username)
                    putString(Constants.PASSWORD, password)
                }.apply()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun nameCheck(name: String): Boolean {
        val regex: Pattern = Pattern.compile("[A-Z][a-z]*")
        val text: Matcher = regex.matcher(name)
        return text.matches()
    }

    private fun emailCheck(email: String): Boolean {
        val regex: Pattern = Pattern.compile("^\\w+([-]?\\w+)*@\\w+([-]?\\w+)*(\\.\\w{2,3})+\$")
        val text: Matcher = regex.matcher(email)
        return text.matches()
    }

    private fun passwordFormatCheck(pass: String): Boolean {
        val regex: Pattern =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}${'$'}")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun birthdayCheck(birthday: String): Boolean {
        val regex: Pattern =
            Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- -.](0[1-9]|1[012])[- -.](19|20)\\d\\d\$")
        val text: Matcher = regex.matcher(birthday)
        return text.matches()
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}