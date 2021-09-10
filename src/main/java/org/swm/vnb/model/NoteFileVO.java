package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteFileVO {

    @ApiModelProperty(hidden = true)
    private Integer fileId;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(required = true, example = "1")
    private Integer folderId;

    @ApiModelProperty(hidden = true)
    private Integer scriptId;

    @ApiModelProperty(required = true, example = "1")
    private Integer categoryId;

    @ApiModelProperty(hidden = true)
    private String categoryName;

    @ApiModelProperty(required = true)
    private String fileName;

    @ApiModelProperty(required = true, example = "0")
    private Integer isImportant;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;
}
