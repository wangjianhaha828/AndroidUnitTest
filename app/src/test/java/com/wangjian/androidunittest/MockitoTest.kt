package com.wangjian.androidunittest

import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.kotlin.*


class MockitoTest {
    //基本使用
    @Test
    fun `Let's verify some behaviour`() {
        //mock creation
        val mockList = mock<MutableList<String>>()

        //using mock object
        mockList.add("one")
        mockList.clear()

        //verification
        verify(mockList).add("one")
        verify(mockList).clear()
    }

    //打桩Stub验证
    @Test
    fun `How about some stubbing`(){
        val mockList = mock<MutableList<String>>()

        whenever(mockList[0]).thenReturn("first")
        whenever(mockList[1]).thenThrow(RuntimeException())

//        verify(mockList, times(1))[0]
    }

    //参数匹配验证
    @Test
    fun `Argument matchers`(){
        val mockList = mock<MutableList<String>>()
        whenever(mockList[anyInt()]).thenReturn("element")
        println(mockList[999])
//        verify(mockList[anyInt()])
        mockList.add("argument")
        //校验参数是否长度大于5
        verify(mockList).add(argThat{ argument->argument.length > 5 })
        //校验参数是某一具体值
        verify(mockList).add(eq("argument"))
        verify(mockList).add("argument")
    }

    //确切数量，0次，1次，多次调用验证
    @Test
    fun `Verifying exact number of invocations, at least x, never`(){
        val mockList = mock<MutableList<String>>()
        mockList.add("once")

        mockList.add("twice")
        mockList.add("twice")

        mockList.add("three times")
        mockList.add("three times")
        mockList.add("three times")

        //验证一次调用
        verify(mockList).add("once")
        verify(mockList, times(1)).add("once")

        //验证确切数量调用
        verify(mockList, times(2)).add("twice")
        verify(mockList, times(3)).add("three times")

        //验证0次调用
        verify(mockList, times(0)).add("zero")
        verify(mockList, never()).add("zero")

        //验证至多/至少调用
        verify(mockList, atMost(1)).add("once")
        verify(mockList, atLeast(1)).add("three times")
        verify(mockList, atLeast(2)).add("three times")
        verify(mockList, atMost(5)).add("three times")
    }

    //抛出异常验证
    @Test
    fun `Stubbing void methods with exceptions`(){
        val mockList = mock<MutableList<String>>()
        doThrow(RuntimeException()).whenever(mockList).clear()
        whenever(mockList.clear()).thenThrow(RuntimeException())
        //抛出异常
        mockList.clear()
    }

    //按顺序验证：不用逐一验证，只要顺序对，可以跳着验证
    @Test
    fun `Verification in order`(){
        //A. 单个mock对象
        val singleMock = mock<MutableList<String>>()
        singleMock.add("was added first")
        singleMock.add("was added second")
        val inOrder = inOrder(singleMock)
        //验证调用顺序
        inOrder.verify(singleMock).add("was added first")
        inOrder.verify(singleMock).add("was added second")

        //B. 多个mock对象
        val firstMock = mock<MutableList<String>>()
        val secondMock = mock<MutableList<String>>()
        firstMock.add("was called first")
        secondMock.add("was called second")
        val inOrder1 = inOrder(firstMock, secondMock)
        inOrder1.verify(firstMock).add("was called first")
        inOrder1.verify(secondMock).add("was called second")

    }
}