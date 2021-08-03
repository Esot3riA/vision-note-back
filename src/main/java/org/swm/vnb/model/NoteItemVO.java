package org.swm.vnb.model;

import lombok.Data;

@Data
public class NoteItemVO {
    public enum ItemType {
        FILE,
        FOLDER
    }

    private ItemType itemType;
    private NoteFileVO noteFile;
    private NoteFolderVO noteFolder;
}
