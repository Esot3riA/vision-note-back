package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserVO implements UserDetails {

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(example = "1")
    private Integer typeId;

    private String email;

    private String password;

    @ApiModelProperty(hidden = true)
    private String authority;

    private String nickname;

    private String avatar;

    private String socialType;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;

    @Override
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(authority));
        return authList;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public String getUsername() {
        return userId.toString();
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isEnabled() {
        return true;
    }

    public boolean isEmpty() {
        return userId == null
                && typeId == null
                && password == null
                && authority == null
                && nickname == null
                && avatar == null
                && socialType == null;
    }
}
