package study.wyy.security.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyaoyao
 * @data 2019-10-30 09:17
 */
@Api(value = "SpringSecurity入门",tags = "SpringSecurity入门")
@RestController
public class HelloSecurityController {


    @ApiOperation("SpringSecurity入门测试")
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Security";
    }
}
