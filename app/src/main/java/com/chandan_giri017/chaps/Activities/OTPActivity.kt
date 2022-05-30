package com.chandan_giri017.chaps.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dial)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        val storedVerificationId = intent.getStringExtra("storedVerificationId")
        binding.verifyButton.setOnClickListener {


            val otp = binding.otpInput.text.toString().trim()
            if (!otp.isEmpty()) {

                dialog.show()

                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential, dialog)
            } else {


                binding.otpInput.error = "Please enter OTP"
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, dialog: Dialog) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                dialog.cancel()
                startActivity(Intent(applicationContext, ProfileSetupActivity::class.java))
                finish()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    dialog.cancel()
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}