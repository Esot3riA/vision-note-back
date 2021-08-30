package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteFolderVO {
    private Integer folderId;
    private Integer userId;
    private Integer parentFolderId;
    private String folderName;
    private String createdAt;
    private String updatedAt;
}
