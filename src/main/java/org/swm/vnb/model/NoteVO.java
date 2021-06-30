package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteVO {
    private Integer id;
    private Integer ownerId;
    private String name;
    private String date;
    private String content;
}
