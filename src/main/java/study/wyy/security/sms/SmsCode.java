package study.wyy.security.sms;

import lombok.Data;
import study.wyy.security.validatecode.ValidateCode;

import java.time.LocalDateTime;

/**
 * @author wyaoyao
 * @data 2019-11-04 11:26
 */
@Data
public class SmsCode extends ValidateCode {

    public SmsCode(String code, int expireIn) {
        this.setCode(code);
        this.setExpireTime(LocalDateTime.now().plusSeconds(expireIn));
    }

}
