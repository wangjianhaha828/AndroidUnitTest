package com.wangjian.androidunittest.robolectric

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wangjian.androidunittest.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var list: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val textView = findViewById<TextView>(R.id.textView)
        val changeTextBtn = findViewById<Button>(R.id.changeTextBtn)
        val startNewActivityBtn = findViewById<Button>(R.id.startNewActivityBtn)
        val showDialog = findViewById<Button>(R.id.showDialog)
        changeTextBtn.setOnClickListener{
            textView.setText("更改后的数据")
        }
        startNewActivityBtn.setOnClickListener{
            startActivity(Intent(this,NewActivity::class.java))
        }
        showDialog.setOnClickListener{
            showDialog()
        }
    }

    private fun showDialog() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this)
            .setMessage("Hello")
            .setTitle("提示")
            .create()
        alertDialog.show()
    }

    override fun onStop() {
        super.onStop()
        list = mutableListOf("one","two")
    }
}