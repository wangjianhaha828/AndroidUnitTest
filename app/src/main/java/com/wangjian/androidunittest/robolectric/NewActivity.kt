package com.wangjian.androidunittest.robolectric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wangjian.androidunittest.R

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
    }
}