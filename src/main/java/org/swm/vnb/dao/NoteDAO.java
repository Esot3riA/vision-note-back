package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface NoteDAO {
    List<NoteFileVO> getNoteFiles(Map<String, Object> params);
    List<NoteFolderVO> getNoteFolders(Map<String, Object> params);
    NoteFolderVO getNoteFolder(Map<String, Object> params);
    Integer getRootFolderId(Integer userId);
    Integer getScriptIdByFileId(Map<String, Object> params);

    List<HashMap<String, Object>> searchNotes(Map<String, Object> params);

    void createNoteFile(NoteFileVO noteFile);
    void updateNoteFile(NoteFileVO noteFile);
    void moveNoteFile(Map<String, Object> params);
    void deleteNoteFile(Map<String, Object> params);
    void deleteNoteFilesByUserId(Integer userId);

    void createNoteFolder(NoteFolderVO noteFolder);
    void updateNoteFolder(NoteFolderVO noteFolder);
    void moveNoteFolder(Map<String, Object> params);
    void deleteNoteFolder(Map<String, Object> params);
    void deleteNoteFoldersByUserId(Integer userId);

    void renewNoteFolder(Map<String, Object> params);
}
