package study.wyy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * @author wyaoyao
 * @data 2019-10-30 10:41
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler mySuccessHandler;
    @Autowired
    AuthenticationFailureHandler myFailHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                 // 配置注册接口和swagger接口不需要身份验证
                .antMatchers("/register").permitAll()
                .antMatchers("/css/**", "/index", "/test/**", "/swagger**").permitAll()
                 // 配置登录页面不被拦截
                .antMatchers("/login.html").permitAll()
                .antMatchers("/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
            // form表单登录
            .formLogin()
                // 指定登录页面
                .loginPage("/login.html")
                // 处理登录请求的url
                .loginProcessingUrl("/auth/form")
                // 修改登录请求的参数key
                .usernameParameter("loginusername")
                .passwordParameter("loginpassword")
                // 配置认证成功处理器
                .successHandler(mySuccessHandler)
                // 配置认证失败处理器
                .failureHandler(myFailHandler);




        http.csrf().disable();
    }
}
