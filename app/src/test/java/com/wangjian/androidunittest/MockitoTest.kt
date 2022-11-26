package com.wangjian.androidunittest

import com.wangjian.androidunittest.demo.Foo
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.withSettings
import org.mockito.kotlin.*


class MockitoTest {
    @Mock
    lateinit var mockList: MutableList<String>

    @Mock
    lateinit var mockFoo: Foo

    @Before
    fun setup() {
        //要使用注解需要调用此语句
        MockitoAnnotations.openMocks(this)
    }

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
    fun `How about some stubbing`() {
        val mockList = mock<MutableList<String>>()

        whenever(mockList[0]).thenReturn("first")
        whenever(mockList[1]).thenThrow(RuntimeException())

//        verify(mockList, times(1))[0]
    }

    //参数匹配验证
    @Test
    fun `Argument matchers`() {
        val mockList = mock<MutableList<String>>()
        whenever(mockList[anyInt()]).thenReturn("element")
        println(mockList[999])
//        verify(mockList[anyInt()])
        mockList.add("argument")
        //校验参数是否长度大于5
        verify(mockList).add(argThat { argument -> argument.length > 5 })
        //校验参数是某一具体值
        verify(mockList).add(eq("argument"))
        verify(mockList).add("argument")
    }

    //确切数量，0次，1次，多次调用验证
    @Test
    fun `Verifying exact number of invocations, at least x, never`() {
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
    fun `Stubbing void methods with exceptions`() {
        val mockList = mock<MutableList<String>>()
        doThrow(RuntimeException()).whenever(mockList).clear()
        whenever(mockList.clear()).thenThrow(RuntimeException())
        //抛出异常
        mockList.clear()
    }

    //按顺序验证：不用逐一验证，只要顺序对，可以跳着验证
    @Test
    fun `Verification in order`() {
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

    //查找冗余调用:
    //verifyNoMoreInteractions()不建议在每个测试方法中使用。滥用它会导致过度指定、可维护性较差的测试。
    //也可以使用never()来实现
    @Test
    fun `Finding redundant invocations`() {
        val mockList = mock<MutableList<String>>()
        mockList.add("one")
        mockList.add("two")
        verify(mockList).add("one")
        //查找冗余调用，将验证失败
        verifyNoMoreInteractions(mockList)
    }

    //连续打桩Stub调用
    @Test
    fun `Stubbing consecutive calls`(){
        //A. 连续打桩，必须连续写，根据调用次数返回不同的结果
        whenever(mockList.add("arg"))
//            .thenThrow(RuntimeException())
            .thenReturn(false)
            .thenReturn(true)
//        mockList.add("arg")
        println(mockList.add("arg"))
        println(mockList.add("arg"))

        //B. 连续打桩，简易版
        whenever(mockList.add("arg"))
            .thenReturn(false,true,false)
        println(mockList.add("arg"))
        println(mockList.add("arg"))
        println(mockList.add("arg"))

        //C. 连续打桩错误写法，会被覆盖,始终返回最后一个结果
        whenever(mockList.add("arg"))
            .thenReturn(false)
        whenever(mockList.add("arg"))
            .thenReturn(true)
    }

    //callback打桩Stub验证
    @Test
    fun `Stubbing with callbacks`(){
        whenever(mockList.toString()).thenAnswer { "callbacks data" }
        println(mockList.toString())
    }

    //doReturn:部分特殊情况使用，一般建议使用whenever(...).thenReturn(...)
    @Test
    fun `doReturn()`(){
        //A. 创建一个真实对象，当使用whenever的时候，空指针或者数组越界会报错
        val mutableList = mutableListOf<String>()
        val spyList = spy(mutableList)
        //报错
//        whenever(spyList[0]).thenReturn("first data")
        //必须需要使用如下写法
        doReturn("first data").whenever(spyList)[0]
        println(spyList[0])

        //B. 重写之前的异常打桩‘
        whenever(mockList.add("arg")).thenThrow(RuntimeException())
        //报错，因为mockList.clear()会抛出异常
//        whenever(mockList.add("arg")).thenReturn(true)
        //必须使用以下写法
        doReturn(true).whenever(mockList).add("arg")
    }

    //doThrow:给void方法打桩Stub，存根void需要不同于when(Object)的方法，因为编译器不喜欢括号内是void方法
    @Test
    fun `doThrow()`(){
        doThrow(RuntimeException()).whenever(mockList).clear()
//        whenever(mockList.clear()).thenThrow(RuntimeException())
        mockList.clear()
    }

    //doAnswer: 当想要给一个void方法打桩Stub的时候使用，存根void需要不同于when(Object)的方法，因为编译器不喜欢括号内是void方法
    @Test
    fun `doAnswer()`(){
        //A. 给void方法打桩
        doAnswer { invocationOnMock ->  {
            val arguments = invocationOnMock.arguments
            "return data"
        } }.whenever(mockList).clear()
        //打印出kotlin.Unit
        println(mockList.clear())

        //A. 给非void方法打桩
        doAnswer { invocationOnMock ->  {
            val arguments = invocationOnMock.arguments
            true
        } }.whenever(mockList).add("arg")
        //报错，只能给void方法打桩
        println(mockList.add("arg"))
    }

    //doNothing: 给void方法打桩，设置void方法不做任何事情,即：失效。特殊情况下，doNothing会有作用
    @Test
    fun `doNothing()`(){
        //A. 对void方法连续打桩调用
        doNothing().doThrow(RuntimeException()).whenever(mockList).clear()
        mockList.clear()
//        mockList.clear()

        //B. 监视真实对象，让它的void方法什么也不做
        val list = mutableListOf<String>()
        val spyList = spy(list)
        //当调用clear()方法的时候什么也不做
        doNothing().whenever(spyList).clear()
        spyList.add("one")
        //spyList的元素仍然存在
        spyList.clear()
        println(spyList.size)
    }

    //doCallRealMethod: 当想调用方法的真正实现时，请使用doCallRealMethod()。一般也是给void方法用
    //一般用来部分模拟，即: 有些方法用模拟的，有些方法用真实的方法
    @Test
    fun `doCallRealMethod`(){
        //会报错，因为doCallRealMethod不能调用抽象方法，必须要有真实实现
        Mockito.doCallRealMethod().whenever(mockList).clear()
        mockList.clear()
    }

    //真实对象打桩
    @Test
    fun `Spying on real objects`(){
        //A. 正常使用
        val list = mutableListOf<String>()
        val spyList = spy(list)
        whenever(spyList.size).thenReturn(100)
        //将调用真实方法
        spyList.add("one")
        spyList.add("two")
        println(spyList[0])
        println(spyList.size)
        verify(spyList).add("one")
        verify(spyList).add("two")

        //B. 正常对象的打桩要正确使用，避免空指针，越界等问题
        val list1 = mutableListOf<String>()
        val spyList1 = spy(list1)
        //下列写法会报错
//        whenever(spyList1[0]).thenReturn("foo")
//        println(spyList1[0])
        //需要使用以下写法去写
        doReturn("foo").whenever(spyList1)[0]
        println(spyList1[0])
    }

    //待探索
    @Test
    fun `Changing default return values of unstubbed invocations`(){

    }

    //捕获参数以进行断言
    //参考：https://blog.csdn.net/liangjing1000/article/details/103788134
    //问题: 目前只能捕获同一类型的参数，比如mockList.add(1,"one")有两种类型，会报错。
    @Test
    fun `Capturing arguments for further assertions`(){
        //A. kotlin中捕获参数
        //创建参数捕获器，只能捕获一个方法的，不能写多个方法
        val argumentCaptor = argumentCaptor<String> {
            mockList.add("one")
        }
        //这一步必须调用，不然没法捕获参数，直接调用argumentCaptor.capture()也没法捕获参数
        verify(mockList).add(argumentCaptor.capture())
        //打印捕获的参数
        println(argumentCaptor.allValues)

        //B. 使用注解创建捕获器

        //C. 无法正确运行的测试
        whenever(mockList.add("two")).thenReturn(true)
        mockList.add("two")
        val ac = ArgumentCaptor.forClass(String::class.java)
        verify(mockList).add(ac.capture())
        //打印捕获的参数
        println(ac.allValues)
    }

    //部分模拟，调用真实方法
    //kotlin中，如果类和方法不加open，默认都是final的，mock对象需要是open的
    @Test
    fun `Real partial mocks`(){
        //模拟对象
        val mockFoo = mock<Foo>()
        //调用真实方法
        whenever(mockFoo.someMethod()).thenCallRealMethod()
        println(mockFoo.someMethod())
    }

    //重置模拟: 不建议使用
    @Test
    fun `Resetting mocks`(){
        whenever(mockList.size).thenReturn(10)
        mockList.add("one")
        reset(mockList)
        mockList.add("two")
        println(mockList.size)
    }

    //withSettings, 不建议使用
    @Test
    fun `Serializable mocks`(){

    }

    //超时验证，多用于多线程，使用较少
    @Test
    fun `Verification with timeout`(){
        mockFoo.someMethod()
        verify(mockFoo, timeout(100)).someMethod()
        verify(mockFoo, timeout(100).times(1)).someMethod()
    }

    //一行代码打桩，简洁写法
    @Test
    fun `One-liner stubs`(){
        mock<Foo> {
            on { someMethod() } doReturn "test"
        }
    }


}