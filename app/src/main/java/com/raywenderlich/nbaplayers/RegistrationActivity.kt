package com.raywenderlich.nbaplayers

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.*
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.widget.doAfterTextChanged
import com.raywenderlich.nbaplayers.databinding.ActivityRegistrationBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE)

        val registrationButton = binding.registrationButton
        registrationButton.isEnabled = false

        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT)
        val date = dateFormat.format(Date())
        binding.birthdayTextView.text = date

        binding.firstNameInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.lastNameInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.usernameInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.emailInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.passwordInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.passwordConfirmationInput.doAfterTextChanged {
            enableRegistrationButton()
        }
        binding.firstNameInput.doAfterTextChanged {
            enableRegistrationButton()
        }

        binding.datePickerButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                { _, pickedYear, pickedMonth, pickedDay ->
                    val bothLessThen10 = "0$pickedDay-0$pickedMonth-$pickedYear"
                    val dayLessThen10 = "0$pickedDay-$pickedMonth-$pickedYear"
                    val monthLessThen10 = "$pickedDay-0$pickedMonth-$pickedYear"
                    val noneLessThen10 = "$pickedDay-$pickedMonth-$pickedYear"
                    when {
                        pickedDay < 10 && pickedMonth < 10 -> binding.birthdayTextView.text =
                            bothLessThen10
                        pickedDay < 10 -> binding.birthdayTextView.text =
                            dayLessThen10
                        pickedMonth < 10 -> binding.birthdayTextView.text =
                            monthLessThen10
                        else -> binding.birthdayTextView.text =
                            noneLessThen10
                    }
                }, year, month, day
            )
            dpd.show()
        }

        val maleCheck = binding.maleCheckBox
        val femaleCheck = binding.femaleCheckBox

        maleCheck.setOnCheckedChangeListener { _, isChecked ->
            femaleCheck.isChecked = !isChecked
            enableRegistrationButton()
        }

        femaleCheck.setOnCheckedChangeListener { _, isChecked ->
            maleCheck.isChecked = !isChecked
            enableRegistrationButton()
        }

        registrationButton.setOnClickListener {
            inputCheck()
        }

        val wikipediaLink = binding.privacyPolicyLink
        wikipediaLink.movementMethod = LinkMovementMethod.getInstance()
        wikipediaLink.setLinkTextColor(Color.BLUE)

        binding.backToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.revealPasswordButton.setOnTouchListener { _, event ->
            val action = event.action
            val passwordField = binding.passwordInput
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    passwordField.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
                MotionEvent.ACTION_UP -> {
                    passwordField.inputType =
                        TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                }
            }
            true
        }

        binding.revealConfirmationButton.setOnTouchListener { _, event ->
            val action = event.action
            val passwordField = binding.passwordConfirmationInput
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    passwordField.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
                MotionEvent.ACTION_UP -> {
                    passwordField.inputType =
                        TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                }
            }
            true
        }
    }

    private fun inputCheck() {
        val firstName = binding.firstNameInput.text?.trim().toString()
        val lastName = binding.lastNameInput.text?.trim().toString()
        val username = binding.usernameInput.text?.trim().toString()
        val email = binding.emailInput.text?.trim().toString()
        val password = binding.passwordInput.text?.trim().toString()
        val passwordConfirm = binding.passwordConfirmationInput.text?.trim().toString()
        val birthday = binding.birthdayTextView.text?.trim().toString()

        when {
            firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()
                    || passwordConfirm.isEmpty() || birthday.isEmpty() ->
                toastMessage(R.string.allFieldsRequired)
            !nameCheck(firstName) -> toastMessage(R.string.firstnameFormat)
            !nameCheck(lastName) -> toastMessage(R.string.lastnameFormat)
            !usernameCheck(username) -> toastMessage(R.string.username_format)
            !emailCheck(email) -> toastMessage(R.string.emailFormat)
            password != passwordConfirm -> toastMessage(R.string.passwordsDontMatch)
            !passwordFormatCheck(password) -> toastMessage(R.string.password_format)
            !birthdayCheck(birthday)->toastMessage(R.string.tooYoung)
            else -> {
                val editor = sharedPreferences.edit()
                editor.putString(Constants.USERNAME, username)
                editor.apply()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun nameCheck(name: String): Boolean {
        val regex: Pattern = Pattern.compile("[A-Za-z]*")
        val text: Matcher = regex.matcher(name)
        return text.matches()
    }

    private fun usernameCheck(username: String): Boolean {
        val regex: Pattern = Pattern.compile("[A-Za-z0-9]{4,}")
        val text: Matcher = regex.matcher(username)
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

    private fun birthdayCheck(birthday: String): Boolean {
        val pickedYear = birthday.takeLast(4).toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (currentYear - pickedYear < 18) {
            return false
        } else {
            return true
        }
    }

    private fun toastMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun enableRegistrationButton() {
        val firstName = binding.firstNameInput.text.toString()
        val lastName = binding.lastNameInput.text.toString()
        val username = binding.usernameInput.text.toString()
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val passwordConfirm = binding.passwordConfirmationInput.text.toString()
        var gender = ""

        when {
            binding.maleCheckBox.isChecked -> gender = Constants.MALE
            binding.femaleCheckBox.isChecked -> gender = Constants.FEMALE
        }

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || gender.isEmpty()) {
            binding.registrationButton.isEnabled = false
        } else {
            binding.registrationButton.isEnabled = true
        }
    }
}