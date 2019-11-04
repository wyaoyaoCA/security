package study.wyy.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wyaoyao
 * @data 2019-11-04 10:01
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
