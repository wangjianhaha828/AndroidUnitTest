package com.wangjian.androidunittest.robolectric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wangjian.androidunittest.R

class WelcomeActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.changeTextBtn)
        button.setOnClickListener{
            textView.setText("更改后的数据")
        }
    }
}