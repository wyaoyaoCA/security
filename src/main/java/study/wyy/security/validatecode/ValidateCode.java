package study.wyy.security.validatecode;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyaoyao
 * @data 2019-11-04 08:10
 * 封装验证码信息的基类
 */
@Data
public class ValidateCode {
    /**
     * 验证码长度
     */
    int length;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 验证码内容
     */
    private String code;

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
