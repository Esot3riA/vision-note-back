package org.swm.vnb.model;

import lombok.Data;

@Data
public class UserTypeVO {
    private Integer type_id;
    private String type_name;
    private String created_at;
    private String updated_at;
}
