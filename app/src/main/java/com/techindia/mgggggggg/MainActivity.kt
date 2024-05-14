package com.techindia.mgggggggg

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techindia.mgggggggg.Activity.DashBoardActivity
import com.techindia.mgggggggg.Activity.LoginActivity
import com.techindia.mgggggggg.Utils.Session


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val logo: ImageView = findViewById(R.id.logo)
      //  val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
      //  logo.startAnimation(anim)
        var session=Session(applicationContext);
        Handler().postDelayed(Runnable {
            if(session.getString("Mobile","")!="" || session.getString("Email","")!="") {
                val intent = Intent(this@MainActivity, DashBoardActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, 2000)

    }
}