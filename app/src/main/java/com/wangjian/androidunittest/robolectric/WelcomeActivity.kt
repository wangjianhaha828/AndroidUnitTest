package com.wangjian.androidunittest.robolectric

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wangjian.androidunittest.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var list: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val textView = findViewById<TextView>(R.id.textView)
        val changeTextBtn = findViewById<Button>(R.id.changeTextBtn)
        val startNewActivityBtn = findViewById<Button>(R.id.startNewActivityBtn)
        changeTextBtn.setOnClickListener{
            textView.setText("更改后的数据")
        }
        startNewActivityBtn.setOnClickListener{
            startActivity(Intent(this,NewActivity::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        list = mutableListOf("one","two")
    }
}