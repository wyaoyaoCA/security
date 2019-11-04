package study.wyy.security.properties;

import lombok.Data;

/**
 * @author wyaoyao
 * @data 2019-11-04 09:25
 */
@Data
public class SmsCodeProperties {

    private int length = 4;
    private int expireTime = 60;
}
