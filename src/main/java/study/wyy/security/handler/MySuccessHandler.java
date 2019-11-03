package study.wyy.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import study.wyy.security.pojo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：wyy
 * @date ：Created in 2019-11-03 19:46
 * @description：自定义认证成功处理器
 * @modified By：
 * @version: $
 */
@Component("mySuccessHandler")
@Slf4j
public class MySuccessHandler implements AuthenticationSuccessHandler {
    /**
     *
     * @param request
     * @param response
     * @param authentication 认证成功的认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 强转成我们的实现
       UserInfo userInfo = (UserInfo) authentication.getPrincipal();
       log.info("欢迎{}", userInfo.getUsername());
        // 这里我们就可以实现我们自己的认证成功逻辑，这里就简单处理一下
        response.getWriter().write("Login Success");

    }
}
