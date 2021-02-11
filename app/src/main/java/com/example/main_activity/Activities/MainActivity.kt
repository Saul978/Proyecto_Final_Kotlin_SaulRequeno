package com.example.main_activity.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.main_activity.Fragments.Fragment_shopping
import com.example.main_activity.Fragments.Fragment_shopping_cart
import com.example.main_activity.Fragments.Fragment_user
import com.example.main_activity.R
import com.example.main_activity.Utiles.Utiles
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = Fragment_shopping.newInstance()
                openFragment(fragment)
                true
            }
            R.id.navigation_dashboard -> {
                val fragment = Fragment_shopping_cart.newInstance()
                openFragment(fragment)
                true
            }
            R.id.navigation_notifications -> {
                val fragment = Fragment_user.newInstance()
                openFragment(fragment)
                true
            }
            else->false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId= R.id.navigation_home
        verificarLogin()
    }

    private fun openFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun verificarLogin() {

        if (Utiles(this).usuario_Actual() == null) {
            val intent = Intent(this, Register_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
