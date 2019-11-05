package study.wyy.security.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.wyy.security.sms.SmsCode;
import study.wyy.security.sms.SmsSender;
import study.wyy.security.validatecode.ImageCode;
import study.wyy.security.validatecode.ValidateCode;
import study.wyy.security.validatecode.ValidateCodeGenerator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wyaoyao
 * @data 2019-11-04 09:02
 */
@RestController
@Slf4j
public class ValidateCodeController {


    @Autowired
    private ValidateCodeGenerator imageValidateCodeGenerator;
    @Autowired
    private ValidateCodeGenerator smsValidateCodeGenerator;
    @Autowired
    private SmsSender defalutSmsSender;

    public static final String IMAGE_CODE_KEY = "SESSION_KEY_IMAGE_CODE";
    public static final String SMS_CODE_KEY = "SESSION_KEY_SMS_CODE";

    @GetMapping("/code/image")
    public void imageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ImageCode code = (ImageCode) imageValidateCodeGenerator.createCode();
        log.info("图片验证码为：{}",code.getCode());
        // 将验证码存到session中，后续验证的时候从session中获取
        request.getSession().setAttribute(IMAGE_CODE_KEY,code);
        ImageIO.write(code.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void smsCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String mobile = request.getParameterValues("mobile")[0];
        log.info("发送的手机号为：{}",mobile);
        SmsCode code = (SmsCode) smsValidateCodeGenerator.createCode();

        log.info("短信验证码为：{}",code.getCode());
        // 将验证码存到session中，后续验证的时候从session中获取
        Map map =  new HashMap<String,SmsCode>();
        map.put(mobile,code);
        request.getSession().setAttribute(SMS_CODE_KEY,map);
        // 发送
        defalutSmsSender.send(mobile,code.getCode());
        response.getWriter().write(code.getCode());
    }
}
