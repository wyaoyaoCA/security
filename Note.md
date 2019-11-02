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

####   增加注册功能

- 处理注册的Rest接口
这里使用了Springsecurity提供的密码加密工具进行了加密
`study.wyy.security.web.controller.RegisterController`
```
@PostMapping("/register")
public String register(@RequestBody UserInfo userInfo){
    log.info("注册用户条的信息：{}",userInfo);
    Long aLong = userInfoService.create(userInfo);
    if(aLong != null){
        return "success";
    }
    return "fail";

}
```
- 注册接口是不需要拦截，配置security不拦截注册请求
声明一个配置类，继承`WebSecurityConfigurerAdapter`,并使用`@EnableWebSecurity`
开启配置
```java
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 配置注册接口和swagger接口不需要身份验证
                .antMatchers("/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .formLogin();
        http.csrf().disable();
    }
}
```

### 2 自定义form表单登录流程
我们已经有了自己注册逻辑，接下来如何自定义自己的登录流程，不使用SpringSecurity提供的表单和登录校验，并接入到SpringSecurity中

#### 2.1 提供一个表单登录页面
`static/login.html`

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body>
<div>
    <form action="/auth/form" method="post">
        <label>用户名</label><input name="username"/>
        <label>密码</label><input name="password"/>
    </form>
</div>

</body>
</html>
```
- 配置我们登录页面和处理登录的请求地址
- 同样配置该页面不需要被拦截和处理登录请求的地址不需要身份验证

#### 2.2 实现`org.springframework.security.core.userdetails.UserDetailsService` 接口
`study.wyy.security.service.impl.UserInfoServiceImpl`实现上述接口（也可重新声明一个该接口的实现类）

- 实现loadUserByUsername方法
该方法内部就可以实现查询自己的数据库的用户信息，并返回UserDetails接口的实现
- 将该实现类注入的Spring容器中，这里之前已经使用`@Service`注解注入到容器中了
#### 2.3 实现UserDetails接口
发现loadUserByUsername方法的返回值是UserDetails，其实这个就是SpringSecurity用于封装
认真信息的接口，所以可以让我们数据库对应的用户表的实体类实现这个接口，当然也可以另外声明实现，将需要的信息封装进去

UserDetails接口介绍：
声明了几个校验用户的的方法，最终交给SpringSecurity去执行，比如用户是否过期，是否冻结等等的
`study.wyy.security.pojo.UserInfo` 这里使用的就是数据表对应的实体类实现了该接口。

#### 2.4 测试
访问hello接口，会跳转到我们的登录页面，输入用户名密码登录

此时会报错，内容如下：
```java
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	at org.springframework.security.crypto.password.DelegatingPasswordEncoder$UnmappedIdPasswordEncoder.matches(DelegatingPasswordEncoder.java:250) ~[spring-security-core-5.2.0.RELEASE.jar:5.2.0.RELEASE]
	at org.springframework.security.crypto.password.DelegatingPasswordEncoder.matches(DelegatingPasswordEncoder.java:198) ~[spring-security-core-5.2.0.RELEASE.jar:5.2.0.RELEASE]
```
SpringSecurity5要求必须配置一个密码加密的工具（容器中必须注入一个PasswordEncoder）
我们注册时候是使用的密码加密工具是BCryptPasswordEncoder，我们在容器中注入即可（前面留下的伏笔）

```java
package study.wyy.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

```

- 提供一个配置类`study.wyy.security.config.BeanConfig`，用于配置bean

再次访问hello接口，跳转到登录页面（这个也是SpringSecurity提供的），登录成功，会跳转到hello接口（SpringSecurity默认的登录成功处理）

### 源码解读
 