package com.chandan_giri017.chaps.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


import android.widget.Toast
import com.chandan_giri017.chaps.R

import com.chandan_giri017.chaps.databinding.ActivityPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
        ////////////////////////////////////////////////////
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dial)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        ////////////////////////////////////////////////
        binding.getOTPButton.setOnClickListener {

            var number = binding.phonebox.text.toString().trim()

            if (number.isNotEmpty()) {
                dialog.show()
                number = "+91" + number
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number) // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this) // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            } else {

                binding.phonebox.error = "Number cannot be empty"
            }


        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {

                dialog.cancel()
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                dialog.cancel()
                startActivity(intent)
                finish()
            }
        }
    }
}