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

    List<NoteFileVO> getNoteFiles(Map<String, String> params);
    List<NoteFolderVO> getNoteFolders(Map<String, String> params);
    Integer getRootFolderId(Integer userId);

    List<HashMap<String, Object>> searchNotes(Map<String, String> params);

    void createNoteFile(NoteFileVO noteFile);
    void createNoteFolder(NoteFolderVO noteFolder);

    void deleteNoteFilesByUserId(Integer userId);
    void deleteNoteFoldersByUserId(Integer userId);
}
