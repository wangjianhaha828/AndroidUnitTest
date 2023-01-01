package com.wangjian.androidunittest.powermock

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)//告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest(PowerMockFinal::class)//对于final类或者有final,private,static,native方法的类，需要列在此处
@PowerMockIgnore("javax.management.*")//忽视提示的classloader错误
class PowerMockFinalTest {

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun doSomeMethodFinal() {
        //普通的mock无法mock final
//        val mock = mock<PowerMockFinal>()
//        whenever(mock.doSomeMethodFinal()).thenReturn("stub data")
//        mock.doSomeMethodFinal()
        //
        val mock1 = PowerMockito.mock(PowerMockFinal::class.java)
        PowerMockito.`when`(mock1.doSomeMethodFinal()).thenReturn("stub data")
        mock1.doSomeMethodFinal()
    }
}