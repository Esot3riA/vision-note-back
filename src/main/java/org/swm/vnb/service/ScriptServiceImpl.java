package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.model.FullScriptVO;
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
    public FullScriptVO getFullScript(Integer scriptId) {
        ScriptVO script = getScript(scriptId);

        FullScriptVO fullScript = new FullScriptVO();
        fullScript.setScript(script);
        if (script != null) {
            Map<String, Object> params = SecurityUtil.getUserParams();
            params.put("scriptId", scriptId.toString());
            fullScript.setScriptParagraphs(scriptDAO.getScriptParagraphs(params));

            Map<String, Object> folderParams = SecurityUtil.getUserParams();
            folderParams.put("folderId", script.getFolderId());
            fullScript.setParentFolder(noteDAO.getNoteFolder(folderParams));
        }

        return fullScript;
    }

    @Override
    public ScriptVO getScript(Integer scriptId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("scriptId", scriptId.toString());

        return scriptDAO.getScript(params);
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
    public void updateScriptRecording(Integer scriptId, Integer isRecording) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("scriptId", scriptId);
        params.put("isRecording", isRecording);

        scriptDAO.updateScriptRecording(params);
    }

    @Override
    public void updateScriptAudio(Integer scriptId, Integer audioId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("scriptId", scriptId);
        params.put("audioFileId", audioId);

        scriptDAO.updateScriptAudio(params);
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
        if (paragraphId == null || paragraph == null || !paragraph.hasPracticalValues()) {
            return;
        }

        Integer currentUserId = SecurityUtil.getCurrentUserId();
        paragraph.setUserId(currentUserId);
        paragraph.setParagraphId(paragraphId);

        scriptDAO.updateParagraph(paragraph);
    }

    @Override
    public void deleteParagraph(Integer paragraphId) {
        Map<String, Object> params = SecurityUtil.getUserParams();
        params.put("paragraphId", paragraphId.toString());

        scriptDAO.deleteParagraph(params);
    }
}
