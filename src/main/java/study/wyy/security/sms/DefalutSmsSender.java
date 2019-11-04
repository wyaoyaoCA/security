package study.wyy.security.sms;

import org.springframework.stereotype.Component;

/**
 * @author wyaoyao
 * @data 2019-11-04 12:39
 */
@Component
public class DefalutSmsSender implements SmsSender {
    /**
     * 这里就模拟发送，实际开发这里应该是调用第三方短信平台发送短信
     * @param mobile
     * @param code
     */
    @Override
    public void send(String mobile, String code) {
        System.out.println("向"+mobile+"发送短信验证码："+code);

    }
}
