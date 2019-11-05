package study.wyy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.wyy.security.service.UserInfoService;
import study.wyy.security.sms.SmsCodeAuthenticationProvider;
import study.wyy.security.sms.SmsValidateCodeGenerator;
import study.wyy.security.validatecode.ImageValidateCodeGenerator;
import study.wyy.security.validatecode.ValidateCodeGenerator;

/**
 * @author wyaoyao
 * @data 2019-10-30 10:23
 */
@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(ImageValidateCodeGenerator.class)
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        return new ImageValidateCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(SmsValidateCodeGenerator.class)
    public ValidateCodeGenerator smsValidateCodeGenerator(){
        return new SmsValidateCodeGenerator();
    }


}
