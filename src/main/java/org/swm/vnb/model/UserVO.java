package org.swm.vnb.model;

import lombok.Data;

@Data
public class UserVO {
    private Integer user_id;
    private Integer type_id;
    private String email;
    private String password;
    private Integer level;
    private String nickname;
    private String avatar;
    private String social_type;
    private String created_at;
    private String updated_at;
}
