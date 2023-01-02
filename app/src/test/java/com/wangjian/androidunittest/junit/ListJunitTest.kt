package com.wangjian.androidunittest.junit

import com.wangjian.androidunittest.matcher.MapMatcher.Companion.hasValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListJunitTest {
    private lateinit var mutableList: MutableList<String>
    private lateinit var mutableMap: MutableMap<Int,String?>


    @Before
    fun setUp(){
        println("测试开始")
        mutableList = mutableListOf<String>("one", "two", "three")
        mutableMap = mutableMapOf<Int, String?>()
        mutableMap[0] = "zero"
        mutableMap[1] = "one"
        mutableMap[3] = null
    }

    @After
    fun tearDown(){
        println("测试结束")
    }

    //
    @Test
    fun addTest(){
        assertEquals("one",mutableList[0])
    }

    //assertThat常用语法
    @Test
    fun assertThatTest(){
        //断言参数等于后面给出的匹配表达式
        assertThat(5, `is`(5))
        //断言参数不等于后面给出的匹配表达式
        assertThat(5, not(6))
        //断言参数相等
        assertThat(30, equalTo(30))
        //断言字符串包含某字符串
        assertThat("abc", containsString("ab"))
        //断言字符串以某字符串开始
        assertThat("abc", startsWith("a"))
        //断言字符串以某字符串结束
        assertThat("abc", endsWith("c"))
        //断言参数的值为null
        assertThat(null, nullValue())
        //断言参数的值不为null
        assertThat("abc", notNullValue())
        //断言符合所有条件，相当于&&
        assertThat(4.0,allOf(`is`(4.0), notNullValue()))
        //断言符合某一条件，相当于或
        assertThat(4.0, anyOf(`is`(3.0), notNullValue()))
        //断言集合有此对象
        assertThat(mutableList, hasItem("one"))
    }

    //自定义Matcher
    @Test
    fun customMatcherTest(){
        //断言map含有此value
        assertThat(mutableMap,hasValue(null))
    }
}