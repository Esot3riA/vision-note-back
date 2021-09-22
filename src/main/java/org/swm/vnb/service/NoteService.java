package org.swm.vnb.service;

import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;
import org.swm.vnb.model.NoteItemVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NoteService {
    List<NoteItemVO> getNoteItems(Integer folderId);
    Map<String, Object> getMyRootNoteInfo();
    List<HashMap<String, Object>> searchNotes(String keyword);

    void createNoteFile(NoteFileVO noteFile);
    void updateNoteFile(Integer fileId, NoteFileVO noteFile);
    void deleteNoteFileAndScript(Integer fileId);

    void createNoteFolder(NoteFolderVO noteFolder);
    void updateNoteFolder(Integer folderId, NoteFolderVO noteFolder);
    void deleteNoteFolder(Integer folderId);

    boolean isValidFolderId(Integer folderId);
}
