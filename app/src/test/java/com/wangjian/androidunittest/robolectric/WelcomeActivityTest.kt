package com.wangjian.androidunittest.robolectric

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.wangjian.androidunittest.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf


@RunWith(RobolectricTestRunner::class)
class WelcomeActivityTest{
    @Test
    fun clickChangeTextButton(){
        Robolectric.buildActivity(WelcomeActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity = controller.get()
            activity.findViewById<Button>(R.id.changeTextBtn).performClick()
            val textView = activity.findViewById<TextView>(R.id.textView)
            println(textView.text)
//            val expectedIntent = Intent(activity, LoginActivity::class.java)
//            val actual: Intent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity()
//            assertEquals(expectedIntent.component, actual.component)
            assertEquals("更改后的数据",textView.text)
        }
    }
}