package study.wyy.security.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import study.wyy.security.properties.SecurityProperties;
import study.wyy.security.validatecode.ValidateCode;
import study.wyy.security.validatecode.ValidateCodeGenerator;

import java.util.Random;

/**
 * @author wyaoyao
 * @data 2019-11-04 11:45
 * 短信验证码生成器
 */
@Slf4j
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public ValidateCode createCode() {
        // 利用随机数生成短信验证码
        String code = "";
        Random random = new Random();
        for (int i = 0; i < securityProperties.getSmsCode().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
        }
        log.info("生成的验证码为：{}",code);

        return new SmsCode(code,60);
    }
}
