package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;

import java.util.List;

@Repository
@Mapper
public interface NoteDAO {
    List<NoteFileVO> getNoteFiles(Integer folderId);
    List<NoteFolderVO> getNoteFolders(Integer folderId);
    List<NoteFileVO> searchNotes(String keyword);
}
