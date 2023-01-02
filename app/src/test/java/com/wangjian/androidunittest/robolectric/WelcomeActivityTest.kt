package com.wangjian.androidunittest.robolectric

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.wangjian.androidunittest.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.Robolectric
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.ShadowToast


@RunWith(RobolectricTestRunner::class)
class WelcomeActivityTest {

    //TextView的内容是否正确
    @Test
    fun clickChangeTextButton() {
        buildActivity(WelcomeActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity = controller.get()
            activity.findViewById<Button>(R.id.changeTextBtn).performClick()
            val textView = activity.findViewById<TextView>(R.id.textView)
            println(textView.text)
            assertEquals("更改后的数据", textView.text)
        }
    }

    //Activity生命周期
    @Test
    fun activityState(){
        val controller  = buildActivity(WelcomeActivity::class.java).setup()
        controller.pause().stop()
//        controller.get().finish()
//        println(controller.get().isFinishing)
        assertFalse(controller.get().list.isNullOrEmpty())
    }

    //是否启动了正确的Intent，获取下一个启动的Activity
    @Test
    fun click_startNewActivity() {
        Robolectric.buildActivity(WelcomeActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity = controller.get()
            activity.findViewById<View>(R.id.startNewActivityBtn).performClick()
            val expectedIntent = Intent(activity, NewActivity::class.java)
            //获取下一个启动的Activity
            val actual = shadowOf(RuntimeEnvironment.getApplication()).nextStartedActivity
            assertEquals(expectedIntent.component, actual.component)
        }
    }

    //Toast验证
    @Test
    fun `retrieves all the toasts that have been displayed`() {
        Toast.makeText(RuntimeEnvironment.getApplication(), "first toast", Toast.LENGTH_SHORT)
            .show()
        Toast.makeText(RuntimeEnvironment.getApplication(), "second toast", Toast.LENGTH_SHORT)
            .show()
        val toasts = shadowOf(RuntimeEnvironment.getApplication()).shownToasts
        toasts.forEach {
            println(it.duration)
            assertEquals(it.duration,Toast.LENGTH_SHORT)
        }
        assertEquals("second toast",ShadowToast.getTextOfLatestToast())
    }

    //Dialog验证(有问题)
    @Test
    fun testDialog(){
        Robolectric.buildActivity(WelcomeActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity = controller.get()

            var latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog()
            //判断dialog尚未弹出
            assertNull(latestAlertDialog)

            activity.findViewById<View>(R.id.showDialog).performClick()
            latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog()
            //判断dialog已经弹出
            assertNotNull(latestAlertDialog)
            val shadowDialog  = shadowOf(latestAlertDialog)
            assertEquals("Hello",shadowDialog.message)
        }

    }

    //访问资源文件
    @Test
    fun testResources(){
        val application = RuntimeEnvironment.getApplication()
        val appName = application.getString(R.string.app_name)
        println(appName)
        assertEquals("AndroidUnitTest",appName)
    }
}