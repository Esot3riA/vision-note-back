package org.swm.vnb.service;

import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteItemVO;

import java.util.List;

public interface NoteService {
    List<NoteItemVO> getNoteItems(Integer folderId);
    List<NoteItemVO> getMyRootNoteItems();
    List<NoteFileVO> searchNotes(String keyword);
}
