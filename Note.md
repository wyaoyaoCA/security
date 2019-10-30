## SpringSecurity

### 1 入门案例

> 项目中导入SpringSecurity的依赖，查看SpringSecurtit提供的默认配置

- 导入SpringSecurity启动器
这里使用的SpringSecurity5
```xml
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- 提供一个Web接口
`study.wyy.security.web.controller.HelloSecurityController`
```java
@Api(value = "SpringSecurity入门",tags = "SpringSecurity入门")
@RestController
public class HelloSecurityController {


    @ApiOperation("SpringSecurity入门测试")
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Security";
    }
}
```

- 访问
`http://127.0.0.1:8080/hello`

会发现跳转到一个登录页面
![security默认登录页面](note_img/security登录页面.jpg)

> 此时用户名为user，密码是在启动项目的时候控制台会输出的

`
Using generated security password: 89a5131d-534d-4dc7-b693-4dbaba502336`

登录成功后就会访问到我们提供的API
这里也继承了Swagger，所以访问swagger-ui.html的时候也是会被拦截需要登录的

> tips

Security5默认提供的是表单登录，之前的版本提供的是httpbasic登录

