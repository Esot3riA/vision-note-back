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
    NoteFolderVO getNoteFolder(Integer folderId);
    List<NoteFolderVO> getAllParentFolders(Integer folderId);
    List<HashMap<String, Object>> searchNotes(String keyword);

    void updateNoteFile(Integer fileId, NoteFileVO noteFile);
    void moveNoteFile(Integer fileId, Integer folderId);
    void deleteNoteFileAndScript(Integer fileId);

    void createNoteFolder(NoteFolderVO noteFolder);
    void updateNoteFolder(Integer folderId, NoteFolderVO noteFolder);
    void moveNoteFolder(Integer folderId, Integer parentFolderId);
    void deleteNoteFolder(Integer folderId);

    boolean isValidFolderId(Integer folderId);
}
