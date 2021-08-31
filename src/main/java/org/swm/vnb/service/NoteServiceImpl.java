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
    public List<NoteItemVO> getNoteItems(Integer folderId, Integer userId) {

        Map<String, String> params = new HashMap<>();
        params.put("folderId", folderId.toString());
        params.put("userId", userId.toString());

        List<NoteFileVO> files = noteDAO.getNoteFiles(params);
        List<NoteFolderVO> folders = noteDAO.getNoteFolders(params);

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
    public List<NoteItemVO> getMyNoteItems(Integer folderId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        return getNoteItems(folderId, currentUserId);
    }

    @Override
    public Map<String, Object> getMyRootNoteInfo() {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Integer rootFolderId = noteDAO.getRootFolderId(currentUserId);
        List<NoteItemVO> noteItems = getNoteItems(rootFolderId, currentUserId);

        Map<String, Object> rootNoteInfo = new HashMap<>();
        rootNoteInfo.put("rootFolderId", rootFolderId);
        rootNoteInfo.put("items", noteItems);

        return rootNoteInfo;
    }

    @Override
    public List<HashMap<String, Object>> searchNotes(String keyword) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, String> params = new HashMap<>();
        params.put("userId", currentUserId.toString());
        params.put("keyword", keyword);

        return noteDAO.searchNotes(params);
    }

    @Override
    public void createNoteFile(NoteFileVO noteFile) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFile.setUserId(currentUserId);

        noteDAO.createNoteFile(noteFile);
    }

    @Override
    public void createNoteFolder(NoteFolderVO noteFolder) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFolder.setUserId(currentUserId);

        noteDAO.createNoteFolder(noteFolder);
    }
}
