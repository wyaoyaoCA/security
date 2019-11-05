package study.wyy.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：wyy
 * @date ：Created in 2019-11-03 20:04
 * @description：自定义认证失败处理器
 * @modified By：
 * @version: $
 */
@Component("myFailHandler")
@Slf4j
public class MyFailHandler implements AuthenticationFailureHandler {
    /**
     *
     * @param request
     * @param response
     * @param exception 认证过程中出现的异常，AuthenticationException是SpringSecurity提供的一个认证过程出现的异常的基类
     *                  如果我们想在认证过程中自定义异常，只要继承这个类，一但发生这个异常就会被这个认证失败处理器处理
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("认证失败 cause by {}", exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(exception.getMessage());

    }
}
