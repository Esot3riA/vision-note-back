package org.swm.vnb.model;

import lombok.Data;

import java.util.List;

@Data
public class ScriptVO {
    private Integer script_id;
    private Integer user_id;
    private Integer file_id;
    private Integer category_id;
    private String title;
    private Integer is_recording;
    private Integer is_liked;
    private String audio_file;
    private String video_file;
    private String create_at;
    private String updated_at;

    private List<ScriptParagraphVO> scriptParagraphs;
}
