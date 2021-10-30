package com.raywenderlich.nbaplayers

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import com.raywenderlich.nbaplayers.databinding.ActivityRegistrationBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
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

        val londonZone = ZoneId.of(Constants.timeZone)
        val currentDate = ZonedDateTime.now(londonZone)
        binding.birthdatTextView.text =
            currentDate.format(DateTimeFormatter.ofPattern(Constants.dateFormat))

        val maleCheck = binding.maleCheckBox
        val femaleCheck = binding.femaleCheckBox
        var gender = ""
        var isMale = false
        var isFemale = false

        fun checkFields() {
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val passwordConfirm = binding.passwordConfirmationInput.text.toString()
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || gender.isEmpty()) {
                registrationButton.isEnabled = false
            } else {
                registrationButton.isEnabled = true
            }
        }

        binding.datePickerButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                { _, year, month, day ->
                    if (day < 10 && month < 10) {
                        binding.birthdatTextView.text = "0$day-0$month-$year"
                    } else if (day < 10) {
                        binding.birthdatTextView.text = "0$day-$month-$year"
                    } else if (month < 10) {
                        binding.birthdatTextView.text = "$day-0$month-$year"
                    } else {
                        binding.birthdatTextView.text = "$day-$month-$year"
                    }
                }, year, month, day)
            dpd.show()
            checkFields()
        }

        maleCheck.setOnClickListener {
            if (!isMale) {
                isMale = true
                isFemale = false
                gender = Constants.male
                femaleCheck.isChecked = false
                checkFields()
            } else {
                isMale = false
                gender = ""
                maleCheck.isChecked = false
                registrationButton.isEnabled = false
            }
        }

        femaleCheck.setOnClickListener {
            if (!isFemale) {
                isFemale = true
                isMale = false
                gender = Constants.female
                maleCheck.isChecked = false
                checkFields()
            } else {
                isFemale = false
                gender = ""
                femaleCheck.isChecked = false
                registrationButton.isEnabled = false
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
        val firstName = binding.firstNameInput.text?.trim().toString()
        val lastName = binding.lastNameInput.text?.trim().toString()
        val username = binding.usernameInput.text?.trim().toString()
        val email = binding.emailInput.text?.trim().toString()
        val password = binding.passwordInput.text?.trim().toString()
        val passwordConfirm = binding.passwordConfirmationInput.text?.trim().toString()
        val birthday = binding.birthdatTextView.text?.trim().toString()

        when {
            firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()
                    || passwordConfirm.isEmpty() || birthday.isEmpty() || gender.isEmpty() ->
                toastMessage(R.string.allFieldsRequired)
            !nameCheck(firstName) -> toastMessage(R.string.firstnameFormat)
            !nameCheck(lastName) -> toastMessage(R.string.lastnameFormat)
            username.length < 4 -> toastMessage(R.string.short_username)
            !emailCheck(email) -> toastMessage(R.string.emailFormat)
            password != passwordConfirm -> toastMessage(R.string.passwordsDontMatch)
            !passwordFormatCheck(password) -> toastMessage(R.string.password_format)
            else -> {
                Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show()
                val editor = sharedPreferences.edit()
                editor.putString(Constants.USERNAME, username)
                editor.apply()
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
        val regex: Pattern =
            Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$")
        val text: Matcher = regex.matcher(email)
        return text.matches()
    }

    private fun passwordFormatCheck(pass: String): Boolean {
        val regex: Pattern =
            Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z]))(?=(.*[\\d]))(?=(.*[\\W]))(?!.*\\s).{8,}\$")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun toastMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}