package study.wyy.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.wyy.security.pojo.UserInfo;

/**
 * @author wyaoyao
 * @data 2019-10-31 11:58
 */
public interface UserInfoService {
    Long create(UserInfo userInfo);

    UserDetails loadUserByMobile(String principal);
}
