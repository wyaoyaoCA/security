package study.wyy.security.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import study.wyy.security.sms.SmsCodeAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：wyy
 * @date ：Created in 2019-11-04 20:21
 * @description：
 * @modified By：
 * @version: $
 */
@Component
@Slf4j
public class SmsCodeAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {


    //public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    //public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    /**
     * 这里获取的请求参数就是用户的手机号
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private boolean postOnly = true;

    /**
     这里处理的就是短信验证码的请求/authentication/mobile
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }
    // 核心逻辑
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 获取请求中提交的手机号
        String mobile = obtainMobile(request);
        log.info("用户输入的手机号为：{}",mobile);
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        // 构造SmsCodeAuthenticationToken，注意此时还未认证的构造方法
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        setDetails(request, authRequest);
        // 进行认证逻辑
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }


    protected void setDetails(HttpServletRequest request,
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}
