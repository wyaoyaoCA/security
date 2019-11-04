package study.wyy.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wyaoyao
 * @data 2019-11-04 09:21
 */
@ConfigurationProperties("wyy.security")
@Data
@Component
public class SecurityProperties {

    private ImageCodeProperties imageCode = new ImageCodeProperties();
    private SmsCodeProperties smsCode = new SmsCodeProperties();
}
