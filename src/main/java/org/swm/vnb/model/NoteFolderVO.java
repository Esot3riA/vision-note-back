package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteFolderVO {
    private Integer folder_id;
    private Integer user_id;
    private Integer parent_folder_id;
    private String folder_name;
    private String created_at;
    private String updated_at;
}
