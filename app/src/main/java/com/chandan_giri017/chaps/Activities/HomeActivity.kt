package com.chandan_giri017.chaps.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chandan_giri017.chaps.R
import com.chandan_giri017.chaps.Classes.User
import com.chandan_giri017.chaps.Adapters.UsersAdapter
import com.chandan_giri017.chaps.databinding.ActivityHomeBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    lateinit var usersList:ArrayList<User>
    lateinit var userRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        ///////////////////   Navigation bar onClickListener //////////////////////////
        binding.navBar.setOnNavigationItemSelectedListener { menuItem ->

            when {
                menuItem.itemId == R.id.account -> {
                    startActivity(Intent(applicationContext, AccountActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.status -> {
                    startActivity(Intent(applicationContext, StatusActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.calls -> {
                    startActivity(Intent(applicationContext, CallsActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.messages -> {

                    return@setOnNavigationItemSelectedListener true
                }


                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }

        }
        //////////////////////////////////////////////////

        //////////////////////////////////////// DATABASE WORKING//////////////////


        userRecyclerView=binding.recyclerView
        userRecyclerView.setHasFixedSize(true)
        usersList= arrayListOf<User>()

        binding.recyclerView.showShimmer()



        database.getReference("Users").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
              if(snapshot.exists())
              {
                  for (userSnapshot in snapshot.children)
                  {
                      val user = userSnapshot.getValue(User::class.java)
                      if(user!!.phoneNumber!=currentUser?.phoneNumber.toString())
                        usersList.add(user!!)
                  }
                  binding.recyclerView.hideShimmer()
                  userRecyclerView.adapter= UsersAdapter(applicationContext,usersList)
              }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()

            }

        })

        /////////////////////////////////////////////////////////////////////






    }

    override fun onResume() {
        super.onResume()

        val currId=auth.uid.toString()

        database.reference.child("Presence").child(currId).setValue("Online")
    }


    ////////////////   onPause method to override transition effect at starting of new activity //////////////////////

    override fun onPause() {
        super.onPause()
        val currId=auth.uid.toString()

        database.reference.child("Presence").child(currId).setValue("Offline")

    }

////////////////    For options menu at the top ////////////////
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search -> Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
            R.id.groups -> Toast.makeText(this, "Groups is clicked", Toast.LENGTH_SHORT).show()
            R.id.invite -> Toast.makeText(this, "Invite clicked", Toast.LENGTH_SHORT).show()
            R.id.logout -> {
                auth.signOut()
                startActivity(Intent(this, PhoneNumberActivity::class.java))
                onPause()
                finishAffinity()
            }
        }

        return super.onOptionsItemSelected(item)
    }
///////////// onCreate method for options menu at the top /////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.topmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}