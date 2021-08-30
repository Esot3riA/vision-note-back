package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface NoteDAO {
    List<NoteFileVO> getNoteFiles(Map<String, String> parentNoteParams);
    List<NoteFolderVO> getNoteFolders(Map<String, String> parentNoteParams);
    Integer getRootFolderId(Integer userId);
    List<NoteFileVO> searchNotes(String keyword);
}
