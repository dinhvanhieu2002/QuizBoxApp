package com.example.quizboxapp.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.quizboxapp.MainActivity
import com.example.quizboxapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout : TextInputLayout
    private lateinit var passwordInputLayout : TextInputLayout
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInputLayout = findViewById(R.id.email)
        passwordInputLayout = findViewById(R.id.password)
        val goBtn = findViewById<Button>(R.id.go_btn)

        firebaseAuth = Firebase.auth
        
        goBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (!validateEmail() or !validatePassword()) {
            return
        }

        val email = emailInputLayout.editText?.text.toString()
        val password = passwordInputLayout.editText?.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                if (task.result.additionalUserInfo?.isNewUser!!) {
                    val user = firebaseAuth.currentUser
                    val email = user!!.email
                    val uid = user.uid
                    val hashMap = HashMap<Any, String?>()
                    hashMap["fullName"] = ""
                    hashMap["email"] = email
                    hashMap["password"] = password
                    hashMap["userName"] = ""
                    hashMap["image"] = ""
                    hashMap["uid"] = uid
                    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                    val reference: DatabaseReference =
                        database.reference
                    reference.child(uid).setValue(hashMap)
                }
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    Toast.makeText(
                        this,
                        "I wish you LUCK!",
                        Toast.LENGTH_LONG
                    ).show()

                    //luu session
                    val user = firebaseAuth.currentUser
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = preferences.edit()

                    if (user != null) {
                        editor.putString("Email", user.email)
                        editor.putString("Id", user.uid)
                        editor.apply()
                    }

                    val mainIntent =
                        Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                } else if (!firebaseAuth.currentUser!!.isEmailVerified) {
                    Toast.makeText(
                        this,
                        "Error: Email is not Verified!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

//    fun forgotPassword(view: View?) {
//        val Close_btn: Button
//        val textInputLayoutEmail: TextInputLayout
//        val Verify_btn: Button
//        val forgotPassAuth: FirebaseAuth
//        val progressBar_load: ProgressBar
//        dialog.setContentView(R.layout.activity_forgot__password)
//        Close_btn = dialog.findViewById<Button>(R.id.close_fp)
//        Verify_btn = dialog.findViewById<Button>(R.id.verify)
//        textInputLayoutEmail = dialog.findViewById(R.id.fp_email)
//        progressBar_load = dialog.findViewById<ProgressBar>(R.id.pro_gress)
//        forgotPassAuth = FirebaseAuth.getInstance()
//        Close_btn.setOnClickListener { dialog.dismiss() }
//
//        ///Button click listener for Verify
//        Verify_btn.setOnClickListener(View.OnClickListener {
//            val email = Objects.requireNonNull(textInputLayoutEmail.editText)
//                .text.toString().trim { it <= ' ' }
//            if (TextUtils.isEmpty(email)) {
//                textInputLayoutEmail.error = "Email Required"
//                return@OnClickListener
//            }
//            forgotPassAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
//
//                ///Send Password reset email to user
//                if (task.isSuccessful) {
//                    progressBar_load.visibility = View.VISIBLE //Load Progress bar
//                    progressBar_load.max = 5000
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "We have sent you instructions to reset your password",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    val mainIntent = Intent(this@LoginActivity, LoginActivity::class.java)
//                    startActivity(mainIntent)
//                } else {
//                    progressBar_load.visibility = View.GONE //Hide Progress Bar
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Failed to send reset email",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        })
//        Objects.requireNonNull(dialog.getWindow())
//            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()
//    }

    private fun validateEmail(): Boolean {
        val value: String = emailInputLayout.editText?.text.toString()
        return if (value.isEmpty()) {
            emailInputLayout.error = "Field cannot be empty"
            false
        } else {
            emailInputLayout.error = null
            emailInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val value: String = passwordInputLayout.editText?.text.toString()
        return if (value.isEmpty()) {
            passwordInputLayout.error = "Field cannot be empty"
            false
        } else {
            passwordInputLayout.error = null
            passwordInputLayout.isErrorEnabled = false
            true
        }
    }

    fun forgotPassword() {

    }
    fun signUp(view : View) {
        val signUpActivity = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(signUpActivity)
    }
}