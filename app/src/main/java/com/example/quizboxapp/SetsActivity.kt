package com.example.quizboxapp

import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.parseIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizboxapp.user.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class SetsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navToggleBtn: Button
    private lateinit var linearLayout: LinearLayout
    private lateinit var setRecyclerView : RecyclerView
    lateinit var db : FirebaseFirestore
    val END_SCALE = 0.7f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sets)

        db = FirebaseFirestore.getInstance()

        cateName = intent.getStringExtra("Category")!!

        setRecyclerView = findViewById(R.id.rv_set)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navToggleBtn = findViewById(R.id.action_menu_presenter)
        linearLayout = findViewById(R.id.main_content)

        loadSet()
        navigationDrawer()
    }

    private fun loadSet() {
        db.collection("QuizBox").document(Companion.cateName!!)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document : DocumentSnapshot = task.result
                    if(document.exists()) {
                        var sets : Long = document.get("Sets") as Long

                        setRecyclerView.adapter = RecyclerSetAdapter(this, sets.toInt())
                    } else {
                        Toast.makeText(this, "No Set Document Exists", Toast.LENGTH_LONG).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun navigationDrawer() {
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.home)
        navToggleBtn.setOnClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) drawerLayout.closeDrawer(
                GravityCompat.START
            ) else drawerLayout.openDrawer(GravityCompat.START)
        }
        animateNavigationDrawer()
    }

    private fun animateNavigationDrawer() {
        drawerLayout.setScrimColor(ContextCompat.getColor(applicationContext, R.color.cat_heading))
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                linearLayout.scaleX = offsetScale
                linearLayout.scaleY = offsetScale
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = linearLayout.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                linearLayout.translationX = xTranslation
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.user_profile -> {
//                val intent = Intent(applicationContext, UserProfileActivity::class.java)
//                startActivity(intent)
//                super@MainActivity.onBackPressed()
            }
            R.id.rate -> {

            }
            R.id.about -> {
                Log.d("TAG", "About clicked")
//                val about = Intent(applicationContext, AboutUsActivity::class.java)
//                startActivity(about)
            }
            R.id.logout -> {
                //Logout
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        }
        return true
    }

    companion object {
        lateinit var cateName : String
    }
}
