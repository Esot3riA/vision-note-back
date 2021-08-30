package org.swm.vnb.model;

import lombok.Data;

import java.util.List;

@Data
public class ScriptVO {
    private Integer scriptId;
    private Integer userId;
    private Integer fileId;
    private Integer categoryId;
    private String title;
    private Integer isRecording;
    private String audioFile;
    private String videoFile;
    private String createdAt;
    private String updatedAt;

    private List<ScriptParagraphVO> scriptParagraphs;
}
