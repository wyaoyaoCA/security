package study.wyy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.wyy.security.web.filter.ImageValidateCodeFilter;


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
    @Autowired
    ImageValidateCodeFilter imageValidateCodeFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                 // 配置注册接口和swagger接口不需要身份验证
                .antMatchers("/register").permitAll()
                .antMatchers("/css/**", "/index", "/test/**", "/swagger**").permitAll()
                 // 配置登录页面不被拦截
                .antMatchers("/login.html").permitAll()
                .antMatchers("/auth/login").permitAll()
                // 配置图片验证码不需要被拦截
                .antMatchers("/code/image").permitAll()
                //配置短信验证码不需要被拦截
                .antMatchers("/code/sms").permitAll()
                .anyRequest()
                .authenticated()
                .and()
            //在SpringSecurity过滤器链上加入图片验证码过滤器，放在UsernamePasswordAuthenticationFilter前面
            .addFilterBefore(imageValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
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
