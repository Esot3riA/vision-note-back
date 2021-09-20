package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.dao.ScriptDAO;
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
    private final ScriptDAO scriptDAO;

    @Autowired
    public NoteServiceImpl(NoteDAO noteDAO, ScriptDAO scriptDAO) {
        this.noteDAO = noteDAO;
        this.scriptDAO = scriptDAO;
    }

    @Override
    public List<NoteItemVO> getNoteItems(Integer folderId, Integer userId) {

        Map<String, Object> params = new HashMap<>();
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

        Map<String, Object> params = new HashMap<>();
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
    public void updateNoteFile(Integer fileId, NoteFileVO noteFile) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFile.setUserId(currentUserId);
        noteFile.setFileId(fileId);

        noteDAO.updateNoteFile(noteFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNoteFileAndScript(Integer fileId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        params.put("fileId", fileId);

        Integer scriptId = noteDAO.getScriptIdByFileId(params);
        params.put("scriptId", scriptId);

        noteDAO.deleteNoteFile(params);

        scriptDAO.deleteParagraphsByScriptId(params);
        scriptDAO.deleteKeywordsByScriptId(params);
        scriptDAO.deleteScript(params);
    }

    @Override
    public void createNoteFolder(NoteFolderVO noteFolder) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFolder.setUserId(currentUserId);

        noteDAO.createNoteFolder(noteFolder);
    }

    @Override
    public void updateNoteFolder(Integer folderId, NoteFolderVO noteFolder) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFolder.setUserId(currentUserId);
        noteFolder.setFolderId(folderId);

        noteDAO.updateNoteFolder(noteFolder);
    }

    @Override
    public void deleteNoteFolder(Integer folderId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        params.put("folderId", folderId);

        noteDAO.deleteNoteFolder(params);
    }

    @Override
    public boolean isValidFolderId(Integer folderId) {
        if (folderId == null) {
            return false;
        }

        Integer currentUserId = SecurityUtil.getCurrentUserId();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        params.put("folderId", folderId);

        NoteFolderVO folder = noteDAO.getNoteFolder(params);
        return folder != null;
    }
}
