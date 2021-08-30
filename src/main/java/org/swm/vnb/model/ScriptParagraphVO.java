package org.swm.vnb.model;

import lombok.Data;

@Data
public class ScriptParagraphVO {
    private Integer paragraphId;
    private Integer scriptId;
    private Integer paragraphSequence;
    private String startTime;
    private String endTime;
    private String paragraphContent;
    private String memoContent;
    private Integer isBookmarked;
    private String createdAt;
    private String updatedAt;
}
