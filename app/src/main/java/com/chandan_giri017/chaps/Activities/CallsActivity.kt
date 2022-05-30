package com.chandan_giri017.chaps.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.databinding.ActivityCallsBinding


class CallsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.navBar.selectedItemId = R.id.calls





        binding.navBar.setOnNavigationItemSelectedListener { menuItem ->

            when {
                menuItem.itemId == R.id.account -> {
                    startActivity(Intent(applicationContext, AccountActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.status -> {
                    startActivity(Intent(applicationContext, StatusActivity::class.java))
                    onPause()
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.calls -> {

                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.messages -> {
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