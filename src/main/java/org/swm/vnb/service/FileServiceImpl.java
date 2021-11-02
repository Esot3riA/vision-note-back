package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.FileDAO;
import org.swm.vnb.model.FileVO;
import org.swm.vnb.util.SecurityUtil;

import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    private final FileDAO fileDAO;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }

    @Override
    public FileVO getFile(Integer fileId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("fileId", fileId);

        return fileDAO.getFile(params);
    }

    @Override
    public FileVO createFile(FileVO file) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        file.setUserId(currentUserId);

        return fileDAO.createFile(file);
    }

    @Override
    public void deleteFile(Integer fileId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("fileId", fileId);

        fileDAO.deleteFile(params);
    }
}
