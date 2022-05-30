package com.chandan_giri017.chaps.Activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

import android.widget.Toast
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.Classes.User

import com.chandan_giri017.chaps.databinding.ActivityProfileSetupBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

import de.hdodenhof.circleimageview.CircleImageView

class ProfileSetupActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var downloadUrl: String


    private lateinit var binding: ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        supportActionBar?.hide()



        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()


        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_sending)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        findViewById<CircleImageView>(R.id.profileImage).setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 11)


        }

        findViewById<Button>(R.id.continueButton).setOnClickListener {

            val name = binding.name.text.toString()
            if (name.isEmpty()) {
                binding.name.error = "Name cannot be empty!!"
            }
            var about = binding.about.text.toString()

            dialog.show()
            if (about.isEmpty())
                about = "Sleeping"

            if (!::downloadUrl.isInitialized)
                downloadUrl = "No image"

            val uid = auth.uid.toString()
            val phoneNumber = auth.currentUser?.phoneNumber.toString()

            val user = User(
                name,
                phoneNumber,
                downloadUrl,
                uid,
                about
            )

            database.reference.child("Users").child(phoneNumber).setValue(user)
                .addOnSuccessListener {
                    dialog.dismiss()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    dialog.dismiss()
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK && requestCode == 11) {
            intent?.data?.let { image ->
                binding.profileImage.setImageURI(image)
                uploadProfileImage(image)
            }
        }
    }

    private fun uploadProfileImage(image: Uri) {
        binding.continueButton.isEnabled = false
        val ref =
            FirebaseStorage.getInstance().reference.child("profiles/${auth.currentUser?.phoneNumber.toString()}")
        val uploadTask = ref.putFile(image)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                binding.continueButton.isEnabled = true
                Toast.makeText(this, "Error while uploading", Toast.LENGTH_SHORT).show()
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                binding.continueButton.isEnabled = true
                downloadUrl = task.result.toString()
            }

        }.addOnFailureListener {
            binding.continueButton.isEnabled = true
        }

    }
}