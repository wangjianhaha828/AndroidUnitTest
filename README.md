# AndroidUnitTest
## Mockito
### [官方文档](https://javadoc.io/static/org.mockito/mockito-core/4.9.0/org/mockito/Mockito.html)
### 添加依赖
*build.gradle*
```
  testImplementation "org.mockito.kotlin:mockito-kotlin:4.0.0"
```
### 使用
*添加注解*
```
@Before
fun setup() {
  //要使用注解需要调用此语句
  MockitoAnnotations.openMocks(this)
}
```
*使用注解*
```
@Mock
lateinit var mockList: MutableList<String>
```
