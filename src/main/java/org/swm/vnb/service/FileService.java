package org.swm.vnb.service;

import org.swm.vnb.model.FileVO;

public interface FileService {
    FileVO getFile(Integer fileId);
    FileVO createFile(FileVO file);
    void deleteFile(Integer fileId);
}
