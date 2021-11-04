package org.swm.vnb.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty(required = false)
    private String memoContent;

    @ApiModelProperty(hidden = true)
    private Integer isBookmarked;

    @ApiModelProperty(hidden = true)
    private String createdAt;

    @ApiModelProperty(hidden = true)
    private String updatedAt;

    @ApiModelProperty(hidden = true)
    private List<ScriptParagraphKeywordVO> keywords;

    public boolean hasPracticalValues() {
        return startTime != null
                || endTime != null
                || paragraphContent != null
                || memoContent != null
                || isBookmarked != null;
    }
}
