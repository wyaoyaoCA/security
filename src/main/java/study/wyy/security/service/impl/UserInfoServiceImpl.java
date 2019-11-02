package study.wyy.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.wyy.security.constant.LockEnum;
import study.wyy.security.mapper.UserInfoMapper;
import study.wyy.security.pojo.UserInfo;
import study.wyy.security.pojo.UserInfoExample;
import study.wyy.security.service.UserInfoService;

import java.util.List;
import java.util.Set;

/**
 * @author wyaoyao
 * @data 2019-10-31 12:01
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService, UserDetailsService {

    private UserInfoMapper userInfoMapper;

    @Override
    public Long create(UserInfo userInfo) {
        try {
            // 密码加密，使用SpringSecurity提供的一个密码BCryptPasswordEncoder加密
             PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(userInfo.getPassword());
            log.info("密码加密结果：{}",encode);

            userInfo.setPassword(encode);
            // 设置用户未冻结
            userInfo.setIsLocked(LockEnum.IS_NOT_LOCKED.isLocked());

            Long insert = userInfoMapper.insert(userInfo);
            return insert;
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户，这里当初没做好，正常用户名查询只能查出一个用户，当时数据库该字段没有设置唯一约束
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        UserInfo userInfo = userInfos.get(0);
        log.info("数据库用户信息：{}",userInfo);
        List<GrantedAuthority> admin = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        log.info("List<GrantedAuthority>: {}", admin);
        // 这里数据库没有存用户权限，这里默认给一组权限，让流程能走通即可，后续在权限部分会重写的
        userInfo.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return userInfo;
    }
}
