package com.chandan_giri017.chaps.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.navBar.selectedItemId = R.id.account

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dial)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        val number = auth.currentUser?.phoneNumber.toString()
        database = Firebase.database
        database.reference.child("Users").child(number).get().addOnSuccessListener {

            val name = it.child("name").value
            val about = it.child("about").value
            val image = it.child("profileImage").value

            if (image != "No image") {
                Glide.with(this).load(image).placeholder(R.drawable.avatar).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        dialog.dismiss()
                        Toast.makeText(applicationContext, "Can't Load Image", Toast.LENGTH_SHORT)
                            .show()
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        dialog.dismiss()
                        return false
                    }

                }).into(binding.profileImage)

            }

            binding.name.setText(name.toString())
            binding.about.setText(about.toString())
            binding.number.setText(number)
            dialog.dismiss()


        }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(applicationContext, "Something Went Wrong ", Toast.LENGTH_SHORT)
                    .show()
            }



        binding.aboutUs.setOnClickListener{
            startActivity(Intent(applicationContext, ChatActivity::class.java))
        }


        binding.navBar.setOnNavigationItemSelectedListener { menuItem ->

            when {
                menuItem.itemId == R.id.account -> {
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId== R.id.status -> {
                    startActivity(Intent(applicationContext, StatusActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener  true
                }

                menuItem.itemId== R.id.calls -> {
                    startActivity(Intent(applicationContext, CallsActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId== R.id.messages -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }



                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }

        }


    }


    override fun onPause() {
        super.onPause()
        finish()
        overridePendingTransition(0, 0)
    }
}