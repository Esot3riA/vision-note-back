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
    public List<NoteItemVO> getNoteItems(Integer folderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId.toString());

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
    public Map<String, Object> getMyRootNoteInfo() {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Integer rootFolderId = noteDAO.getRootFolderId(currentUserId);
        List<NoteItemVO> noteItems = getNoteItems(rootFolderId);

        Map<String, Object> rootNoteInfo = new HashMap<>();
        rootNoteInfo.put("rootFolderId", rootFolderId);
        rootNoteInfo.put("items", noteItems);

        return rootNoteInfo;
    }

    @Override
    public NoteFolderVO getNoteFolder(Integer folderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);

        return noteDAO.getNoteFolder(params);
    }

    @Override
    public List<NoteFolderVO> getAllParentFolders(Integer folderId) {
        List<NoteFolderVO> parentFolders = new ArrayList<>();

        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);

        NoteFolderVO parentFolder = noteDAO.getNoteFolder(params);
        while (!isRootFolder(parentFolder)) {
            parentFolders.add(parentFolder);

            params.put("folderId", parentFolder.getParentFolderId());
            parentFolder = noteDAO.getNoteFolder(params);
        }
        parentFolders.add(parentFolder);

        return parentFolders;
    }

    private boolean isRootFolder(NoteFolderVO folder) {
        return (folder != null) && (folder.getParentFolderId()) == null;
    }

    @Override
    public List<HashMap<String, Object>> searchNotes(String keyword) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("keyword", keyword);

        return noteDAO.searchNotes(params);
    }

    @Override
    public void updateNoteFile(Integer fileId, NoteFileVO noteFile) {
        if (fileId == null || noteFile == null || !noteFile.hasPracticalValues()) {
            return;
        }

        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFile.setUserId(currentUserId);
        noteFile.setFileId(fileId);
        noteDAO.updateNoteFile(noteFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveNoteFile(Integer fileId, Integer folderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("fileId", fileId);
        params.put("folderId", folderId);

        noteDAO.moveNoteFile(params);

        renewFolderRecursive(folderId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNoteFileAndScript(Integer fileId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("fileId", fileId);

        Integer scriptId = noteDAO.getScriptIdByFileId(params);
        params.put("scriptId", scriptId);

        noteDAO.deleteNoteFile(params);

        scriptDAO.deleteKeywordsByScriptId(params);
        scriptDAO.deleteParagraphsByScriptId(params);
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
        if (folderId == null || noteFolder == null || !noteFolder.hasPracticalValues()) {
            return;
        }

        Integer currentUserId = SecurityUtil.getCurrentUserId();
        noteFolder.setUserId(currentUserId);
        noteFolder.setFolderId(folderId);

        noteDAO.updateNoteFolder(noteFolder);
    }

    @Override
    public void moveNoteFolder(Integer folderId, Integer parentFolderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);
        params.put("parentFolderId", parentFolderId);

        noteDAO.moveNoteFolder(params);

        renewFolderRecursive(parentFolderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNoteFolder(Integer folderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);

        deleteFolderRecursive(params);
    }

    private void deleteFolderRecursive(Map<String, Object> params) {
        List<NoteFileVO> files = noteDAO.getNoteFiles(params);
        List<NoteFolderVO> folders = noteDAO.getNoteFolders(params);

        for (NoteFolderVO folder : folders) {
            Map<String, Object> lowerFolderParams = new HashMap<>();
            lowerFolderParams.put("userId", params.get("userId"));
            lowerFolderParams.put("folderId", folder.getFolderId());

            deleteFolderRecursive(lowerFolderParams);
        }
        for (NoteFileVO file : files) {
            deleteNoteFileAndScript(file.getFileId());
        }

        noteDAO.deleteNoteFolder(params);
    }

    @Override
    public boolean isValidFolderId(Integer folderId) {
        if (folderId == null) {
            return false;
        }

        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);

        NoteFolderVO folder = noteDAO.getNoteFolder(params);
        return folder != null;
    }

    void renewFolderRecursive(Integer folderId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("folderId", folderId);

        NoteFolderVO folder = noteDAO.getNoteFolder(params);
        while (!isRootFolder(folder)) {
            noteDAO.renewNoteFolder(params);

            params.put("folderId", folder.getParentFolderId());
            folder = noteDAO.getNoteFolder(params);
        }
        noteDAO.renewNoteFolder(params);
    }
}
