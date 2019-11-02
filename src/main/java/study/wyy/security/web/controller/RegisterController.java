package study.wyy.security.web.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.wyy.security.constant.LockEnum;
import study.wyy.security.pojo.UserInfo;
import study.wyy.security.service.UserInfoService;

/**
 * @author wyaoyao
 * @data 2019-10-30 10:02
 */
@Api(value = "注册",tags = "注册")
@RestController
@AllArgsConstructor
@Slf4j
public class RegisterController {


    private UserInfoService userInfoService;

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo){
        log.info("注册用户条的信息：{}",userInfo);
        Long aLong = userInfoService.create(userInfo);
        if(aLong != null){
            return "success";
        }
        return "fail";

    }
}
