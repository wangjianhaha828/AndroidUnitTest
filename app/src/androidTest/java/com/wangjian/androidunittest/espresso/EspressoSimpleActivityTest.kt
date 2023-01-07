package com.wangjian.androidunittest.espresso

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.wangjian.androidunittest.R
import org.hamcrest.Matchers.*
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoSimpleActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(EspressoSimpleActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun onCreate() {
    }

    //验证文本内容
    @Test
    fun test_EspressoSimpleActivity_changeText(){
        //点击按钮
        onView(withId(R.id.changeTextBtn)).perform(click())
        //验证文本内容
        onView(withId(R.id.changeText)).check(matches(withText("Hello Espresso!")))
    }

    //onData使用,Spinner
    @Test
    fun test_EspressoSimpleActivity_onSpinnerSelect(){
        //点击spinner
        onView(withId(R.id.planets_spinner)).perform(click())
        //Spinner 会创建一个包含其内容的 ListView。此视图可能会很长，
        // 并且相应元素可能不会放到视图层次结构中。通过使用 onData()，
        // 我们可以强制将所需元素放到视图层次结构中。
        // Spinner 中的项目是字符串，因此我们要匹配等于字符串 "Americano" 的项目：
        onData(allOf(`is`(instanceOf(String::class.java)),
            `is`("2"))).perform(click())
        //验证文本是否正确
        onView(withId(R.id.spinner_text))
            .check(matches(withText(containsString("2"))))
    }
}