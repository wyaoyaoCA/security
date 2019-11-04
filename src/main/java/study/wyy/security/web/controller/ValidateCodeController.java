package study.wyy.security.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.wyy.security.validatecode.ImageCode;
import study.wyy.security.validatecode.ValidateCodeGenerator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wyaoyao
 * @data 2019-11-04 09:02
 */
@RestController
@Slf4j
public class ValidateCodeController {


    @Autowired
    private ValidateCodeGenerator imageValidateCodeGenerator;

    public static final String IMAGE_CODE_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("/code/image")
    public void imageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ImageCode code = (ImageCode) imageValidateCodeGenerator.createCode();
        log.info("图片验证码为：{}",code.getCode());
        // 将验证码存到session中，后续验证的时候从session中获取
        request.getSession().setAttribute(IMAGE_CODE_KEY,code);
        ImageIO.write(code.getImage(), "JPEG", response.getOutputStream());
    }
}
