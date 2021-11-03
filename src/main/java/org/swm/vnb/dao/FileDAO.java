package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.FileVO;

import java.util.Map;

@Repository
@Mapper
public interface FileDAO {
    FileVO getFile(Map<String, Object> params);
    void createFile(FileVO file);
    void deleteFile(Map<String, Object> params);
}
