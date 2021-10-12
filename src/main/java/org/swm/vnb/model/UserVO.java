package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class UserVO implements UserDetails {

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @NotNull
    @ApiModelProperty(required = true, example = "1")
    private Integer typeId;

    @Email
    @NotBlank
    @ApiModelProperty(required = true, value="이메일 형식을 따라야 한다.")
    private String email;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
    @NotBlank
    @ApiModelProperty(required = true, value="영문 대소문자와 특수문자가 결합된 8자 이상 20자 이하의 비밀번호여야 한다.")
    private String password;

    @ApiModelProperty(hidden = true)
    private String authority;

    @NotBlank
    @ApiModelProperty(required = true)
    private String nickname;

    @NotBlank
    @ApiModelProperty(required = true)
    private String avatar;

    @NotBlank
    @ApiModelProperty(required = true)
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

    public boolean hasPracticalValues() {
        return typeId != null
                || password != null
                || authority != null
                || nickname != null
                || avatar != null
                || socialType != null;
    }
}
