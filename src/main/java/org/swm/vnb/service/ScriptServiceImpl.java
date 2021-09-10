package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;
import org.swm.vnb.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScriptServiceImpl implements ScriptService {
    private final ScriptDAO scriptDAO;
    private final NoteDAO noteDAO;

    @Autowired
    public ScriptServiceImpl(ScriptDAO scriptDAO, NoteDAO noteDAO) {
        this.scriptDAO = scriptDAO;
        this.noteDAO = noteDAO;
    }

    @Override
    public ScriptVO getMyScript(Integer scriptId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, String> params = new HashMap<>();
        params.put("scriptId", scriptId.toString());
        params.put("userId", currentUserId.toString());

        ScriptVO script = scriptDAO.getScript(params);
//        if (script != null) {
//            script.setScriptParagraphs(scriptDAO.getScriptParagraphs(params));
//        }
        return script;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createScriptAndFile(ScriptVO script) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        script.setUserId(currentUserId);
        scriptDAO.createScript(script);

        NoteFileVO noteFile = new NoteFileVO();
        noteFile.setUserId(currentUserId);
        noteFile.setFolderId(noteDAO.getRootFolderId(currentUserId));
        noteFile.setScriptId(script.getScriptId());
        noteFile.setCategoryId(script.getCategoryId());
        noteFile.setFileName(script.getFileName());
        noteFile.setIsImportant(0);

        noteDAO.createNoteFile(noteFile);
    }

    @Override
    public void createParagraph(Integer scriptId, ScriptParagraphVO paragraph) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        paragraph.setScriptId(scriptId);
        paragraph.setUserId(currentUserId);

        scriptDAO.createParagraph(paragraph);
    }

    @Override
    public void updateParagraph(Integer paragraphId, ScriptParagraphVO paragraph) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        paragraph.setUserId(currentUserId);
        paragraph.setParagraphId(currentUserId);

        scriptDAO.updateParagraph(paragraph);
    }

    @Override
    public void deleteParagraph(Integer paragraphId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, String> params = new HashMap<>();
        params.put("paragraphId", paragraphId.toString());
        params.put("userId", currentUserId.toString());

        scriptDAO.deleteParagraph(params);
    }
}
