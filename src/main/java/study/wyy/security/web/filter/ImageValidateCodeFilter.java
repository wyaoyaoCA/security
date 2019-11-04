package study.wyy.security.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import study.wyy.security.exception.ValidateCodeException;
import study.wyy.security.handler.MyFailHandler;
import study.wyy.security.validatecode.ImageCode;
import study.wyy.security.web.controller.ValidateCodeController;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * OncePerRequestFilter 是Spring提供的一个过滤器工具类，保证一次请求只会调用一次
 */
@Slf4j
@Component
public class ImageValidateCodeFilter extends OncePerRequestFilter {
    // 注入失败处理器，处理验证失败

    @Autowired
    MyFailHandler myFailHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("请求路径URI：{}", httpServletRequest.getRequestURI());
        log.info("请求路径URL：{}", httpServletRequest.getRequestURL());
        // 该过滤器只拦截登录页面，主要用于校验验证码是否正确，
        if("/auth/form".equals(httpServletRequest.getRequestURI()) && RequestMethod.POST.toString().equals(httpServletRequest.getMethod())){
            try {
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                log.info(e.getMessage());
                myFailHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        HttpSession session = servletWebRequest.getRequest().getSession();
        ImageCode codeInSession = (ImageCode) session.getAttribute(ValidateCodeController.IMAGE_CODE_KEY);
        // 获取请求中提交的图片验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            session.removeAttribute(ValidateCodeController.IMAGE_CODE_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!codeInRequest.equals(codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }
        session.removeAttribute(ValidateCodeController.IMAGE_CODE_KEY);
    }
}
