package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScriptParagraphVO {

    @ApiModelProperty(hidden = true)
    private Integer paragraphId;

    @ApiModelProperty(hidden = true)
    private Integer scriptId;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(required = true, example = "1")
    private Integer paragraphSequence;

    @ApiModelProperty(required = true, example = "00:00")
    private String startTime;

    @ApiModelProperty(required = true, example = "01:00")
    private String endTime;

    @ApiModelProperty(required = true)
    private String paragraphContent;

    @ApiModelProperty(required = true)
    private String memoContent;

    @ApiModelProperty(required = true, example = "0")
    private Integer isBookmarked;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;
}
