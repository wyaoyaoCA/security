package study.wyy.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wyaoyao
 * @data 2019-10-30 10:26
 */
@Getter
@AllArgsConstructor
public enum LockEnum {

    /**
     * 用户冻结
     */
    IS_LOCKED(true,"用户冻结"),
    /**
     * 用户未冻结
     */
    IS_NOT_LOCKED(false,"用户未冻结");

    private boolean isLocked;
    private String desc;
}
