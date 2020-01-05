package com.hishamabifarah.androidx_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.single_movie_details.SingleMovie
import com.hishamabifarah.androidx_kotlin.navigationComponentsApp.MainActivity

class HomeActivity : AppCompatActivity() , View.OnClickListener {

    //todo how to log messages in Kotlin
    //todo find best way to implement button click and finding views inside Activity and Fragment (butterknife?)
    //todo read https://antonioleiva.com/lambdas-kotlin-android/ for usage of Lambdas in views click

    //todo notes: implement button click inside Activity
        // check MainFragment.kt for implementation inside Fragment
        // implement View.OnClickListener interface
        // findViewById<Button>(R.id.button).setOnClickListener(this)
        // override onClick(v:View?) method
        // use when() to check which button is clicked and start intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.button).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        var intent : Intent? = null
        when(v!!.id){
            R.id.button ->{
                intent = Intent(applicationContext,
                    MainActivity::class.java)

            }
            R.id.button2 ->{

                intent = Intent(applicationContext,
                    SingleMovie::class.java)
                intent.putExtra("id", 284053)

            }
            R.id.button3 ->{
                intent = Intent(applicationContext,
                    MainActivity::class.java)

            }
            R.id.button4 ->{
                intent = Intent(applicationContext,
                    MainActivity::class.java)

            }
        }
        startActivity(intent)
    }
}
