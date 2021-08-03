package org.swm.vnb.model;

import lombok.Data;

@Data
public class ScriptParagraphVO {
    private Integer paragraph_id;
    private Integer script_id;
    private Integer paragraph_sequence;
    private String start_time;
    private String end_time;
    private String paragraph_content;
    private String memo_content;
    private Integer is_bookmarked;
    private String created_at;
    private String updated_at;
}
