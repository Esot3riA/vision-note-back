package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteFileVO {
    private Integer file_id;
    private Integer user_id;
    private Integer folder_id;
    private Integer category_id;
    private String file_name;
    private Integer is_important;
    private String created_at;
    private String updated_at;
}
