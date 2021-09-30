package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScriptVO {

    @ApiModelProperty(hidden = true)
    private Integer scriptId;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(required = true, example = "0")
    private Integer categoryId;

    @ApiModelProperty(hidden = true)
    private String categoryName;

    @ApiModelProperty(required = true, example = "Test title")
    private String fileName;

    @ApiModelProperty(required = true, example = "0")
    private Integer isRecording;

    private String audioFile;

    private String videoFile;

    @ApiModelProperty(hidden = true)
    private Integer folderId;

    @ApiModelProperty(hidden = true)
    private Integer isImportant;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;
}
