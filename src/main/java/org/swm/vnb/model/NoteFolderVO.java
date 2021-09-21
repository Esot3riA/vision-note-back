package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteFolderVO {

    @ApiModelProperty(hidden = true)
    private Integer folderId;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(required = true, example = "1")
    private Integer parentFolderId;

    @ApiModelProperty(required = true)
    private String folderName;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;

    public boolean isExistUpdateElements() {
        return folderName != null || parentFolderId != null;
    }
}
