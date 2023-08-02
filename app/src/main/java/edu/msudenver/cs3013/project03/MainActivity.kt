package edu.msudenver.cs3013.project03

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val USER_NAME_KEY = "USER_NAME_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"
//constants

const val IS_LOGGED_IN = "IS_LOGGED_IN"
const val LOGGED_IN_USERNAME = "LOGGED_IN_USERNAME"

//Username and password to use
const val USER_NAME_CORRECT_VALUE = "dhelms2"
const val PASSWORD_CORRECT_VALUE = "Manchester"

class MainActivity : AppCompatActivity() {
    //initalizations below
//    var isDualPane: Boolean = false

    private val submitButton: Button //button to submit login info
        get() = findViewById(R.id.submit_button)

    private val userName: EditText //username field
        get() = findViewById(R.id.user_name)

    private val password: EditText //password field
        get() = findViewById(R.id.password)

    override fun onCreate(savedInstanceState: Bundle?) { //on create when it launches
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener { //when clicked

            val userNameForm = userName.text.toString().trim() //removes whitespace
            val passwordForm = password.text.toString().trim()

            hideKeyboard() //hides keyboard, found in fun below

            if (userNameForm == USER_NAME_CORRECT_VALUE && passwordForm == PASSWORD_CORRECT_VALUE) { //checking against stored username/password

                //Set the name of the activity to launch
                Intent(this, SecondActivity::class.java).also { welcomeIntent ->
                    //Add the data prepare to be sent to second activity
                    welcomeIntent.putExtra(USER_NAME_KEY, userNameForm)
                    welcomeIntent.putExtra(PASSWORD_KEY, passwordForm)

                    //Reset text fields to blank
                    this.userName.text.clear()
                    this.password.text.clear()

                    //Launch activity
                    startActivity(welcomeIntent)
                }
            } else { //if username/password is incorrect
                val toast = Toast.makeText(
                    this,
                    getString(R.string.login_form_entry_error), //display this message
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

        }
    }
//
//    fun onSelected(id: Int) {
//        if (isDualPane) {
//            val detailFragment =
//                supportFragmentManager
//                    .findFragmentById(
//                        R.id.fragment_account
//                    ) as
//                        AccountFragment
//
////            AccountFragment.setStarSignData(id)
//        }

    fun hideKeyboard() { //hides keyboard
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}