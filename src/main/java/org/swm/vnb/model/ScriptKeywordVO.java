package org.swm.vnb.model;

import lombok.Data;

@Data
public class ScriptKeywordVO {
    private Integer keywordId;
    private Integer scriptId;
    private Integer userId;
    private String keyword;
    private String createdAt;
    private String updatedAt;
}
