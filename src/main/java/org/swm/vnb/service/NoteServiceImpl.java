package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;
import org.swm.vnb.model.NoteItemVO;
import org.swm.vnb.model.NoteItemVO.ItemType;
import org.swm.vnb.util.SecurityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteDAO noteDAO;

    @Autowired
    public NoteServiceImpl(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    public List<NoteItemVO> getMyNoteItems(Integer folderId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        return getNoteItems(folderId, currentUserId);
    }

    @Override
    public List<NoteItemVO> getMyRootNoteItems() {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        Integer rootFolderId = noteDAO.getRootFolderId(currentUserId);

        return getNoteItems(rootFolderId, currentUserId);
    }

    public List<NoteItemVO> getNoteItems(Integer folderId, Integer userId) {

        Map<String, String> parentNoteParams = new HashMap<>();
        parentNoteParams.put("folderId", folderId.toString());
        parentNoteParams.put("userId", userId.toString());

        List<NoteFileVO> files = noteDAO.getNoteFiles(parentNoteParams);
        List<NoteFolderVO> folders = noteDAO.getNoteFolders(parentNoteParams);

        List<NoteItemVO> noteItems = new ArrayList<>();
        for (NoteFileVO file : files) {
            NoteItemVO noteItem = new NoteItemVO();
            noteItem.setItemType(ItemType.FILE);
            noteItem.setNoteFile(file);
            noteItems.add(noteItem);
        }
        for (NoteFolderVO folder : folders) {
            NoteItemVO noteItem = new NoteItemVO();
            noteItem.setItemType(ItemType.FOLDER);
            noteItem.setNoteFolder(folder);
            noteItems.add(noteItem);
        }

        return noteItems;
    }

    @Override
    public List<NoteFileVO> searchNotes(String keyword) {
        return noteDAO.searchNotes(keyword);
    }
}
