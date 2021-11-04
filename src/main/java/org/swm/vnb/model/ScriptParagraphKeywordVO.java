package org.swm.vnb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptParagraphKeywordVO {
    private Integer keywordId;
    private Integer paragraphId;
    private Integer scriptId;
    private Integer userId;
    private String keyword;
    private String createdAt;
    private String updatedAt;
}
