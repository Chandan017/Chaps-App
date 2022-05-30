package com.chandan_giri017.chaps.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.databinding.ActivityStatusBinding


class StatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.navBar.selectedItemId = R.id.status




        binding.navBar.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.account -> {
                    startActivity(Intent(applicationContext, AccountActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.status -> {

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.calls -> {
                    startActivity(Intent(applicationContext, CallsActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.messages -> {
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
        finish()
        super.onPause()
        overridePendingTransition(0, 0)
    }
}