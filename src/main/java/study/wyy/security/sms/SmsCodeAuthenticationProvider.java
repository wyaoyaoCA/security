package study.wyy.security.sms;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;
import study.wyy.security.exception.ValidateCodeException;
import study.wyy.security.service.UserInfoService;
import study.wyy.security.web.controller.ValidateCodeController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Data
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserInfoService userInfoService;

    /**
     *
     * @param authentication 就是封装认证信息的token，这里就是SmsCodeAuthenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 强转
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
        String mobile = (String) smsCodeAuthenticationToken.getPrincipal();
        // 这里进行我们的短信验证码的校验，也可以想图片验证码那样写一个过滤器，在过滤器中进行校验
        checkSmsCode(mobile);
        // 调用UserDetailsService去查询数据库
        UserDetails userDetails = userInfoService.loadUserByMobile((String)smsCodeAuthenticationToken.getPrincipal());
        // 再次构建SmsCodeAuthenticationToken，此时已经是认证通过的,Principal属性封装的就是我们的认证通过的用户信息也就是UserDetails
        // 的实现
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    /**
     * 校验验证码
     * @param mobile
     */
    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取用户输入的验证码
        String inputCode = request.getParameter("smsCode");
        log.info("用户输入的短信验证码为：{}", inputCode);
        Map<String, SmsCode> smsCode = (Map<String, SmsCode>) request.getSession().getAttribute(ValidateCodeController.SMS_CODE_KEY);
        log.info("session中的短信验证码为：{}", smsCode.get(mobile).getCode());
        if(smsCode == null) {
            throw new ValidateCodeException("未检测到申请短信验证码");
        }
        if(!(smsCode.get(mobile).getCode().equals(inputCode))){
            throw new ValidateCodeException("短信验证码错误");
        }


    }

    /**
     * 根据这个方法，来确定当前的provider是否支持处理传入的AuthenticationToken
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
