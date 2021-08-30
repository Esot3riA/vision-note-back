package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteFileVO {
    private Integer fileId;
    private Integer userId;
    private Integer folderId;
    private Integer categoryId;
    private String categoryName;
    private String fileName;
    private Integer isImportant;
    private String createdAt;
    private String updatedAt;
}
