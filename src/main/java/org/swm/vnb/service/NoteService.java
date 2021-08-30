package org.swm.vnb.service;

import org.swm.vnb.model.NoteItemVO;

import java.util.HashMap;
import java.util.List;

public interface NoteService {
    List<NoteItemVO> getNoteItems(Integer folderId, Integer userId);
    List<NoteItemVO> getMyNoteItems(Integer folderId);
    List<NoteItemVO> getMyRootNoteItems();
    List<HashMap<String, Object>> searchNotes(String keyword);
}
