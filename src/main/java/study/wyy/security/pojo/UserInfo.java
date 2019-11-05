package study.wyy.security.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

@Data
public class UserInfo implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private Boolean isLocked;

    private Date createdAt;

    private Date updatedAt;

    private String mobile;

    /**
     * 用于封装用户的权限
     */
    private List<GrantedAuthority> authorities;

    /**
     * UserDetails 接口声明的方法，
     * @return 用于实现校验用户账号是否过期的逻辑
     */
    @Override
    public boolean isAccountNonExpired() {
        // 这里就不做实现，直接返回true
        return true;
    }
    /**
     * UserDetails 接口声明的方法，
     * @return 用于实现校验用户账号是否冻结
     */
    @Override
    public boolean isAccountNonLocked() {

        return !getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * UserDetails 接口声明的方法，
     * @return 用于实现校验用户账号是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}