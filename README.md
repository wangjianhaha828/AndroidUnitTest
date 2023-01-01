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

## Robolectric
### [官方文档](https://robolectric.org/)
### 添加依赖
*build.gradle*
```
  android {
  testOptions {
    unitTests {
      includeAndroidResources = true
    }
  }
}

dependencies {
  testImplementation 'junit:junit:4.13.2'
  testImplementation 'org.robolectric:robolectric:4.9'
}
```
*gradle.properties(AndroidStudio3.3+无须添加)*
```
android.enableUnitTestBinaryResources=true
```
### 使用
*在test目录下的测试类添加注解*
```
@RunWith(RobolectricTestRunner::class)
class WelcomeActivityTest{
}
```
