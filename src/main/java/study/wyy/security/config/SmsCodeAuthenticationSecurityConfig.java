package study.wyy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import study.wyy.security.handler.MyFailHandler;
import study.wyy.security.handler.MySuccessHandler;
import study.wyy.security.service.UserInfoService;
import study.wyy.security.sms.SmsCodeAuthenticationProvider;
import study.wyy.security.web.filter.SmsCodeAuthenticationFilter;

/**
 * @author wyaoyao
 * @data 2019-11-05 10:24
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    MyFailHandler myFailHandler;
    @Autowired
    MySuccessHandler mySuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 设置SmsCodeAuthenticationFilter
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 设置我们的失败成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(mySuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myFailHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserInfoService(userInfoService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}

