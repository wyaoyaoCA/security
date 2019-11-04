package study.wyy.security.sms;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import study.wyy.security.service.UserInfoService;



@Data
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
        // 调用UserDetailsService去查询数据库
        UserDetails userDetails = userInfoService.loadUserByMobile((String)smsCodeAuthenticationToken.getPrincipal());
        // 再次构建SmsCodeAuthenticationToken，此时已经是认证通过的
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        return null;
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
