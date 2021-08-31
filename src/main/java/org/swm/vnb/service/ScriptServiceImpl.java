package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;
import org.swm.vnb.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScriptServiceImpl implements ScriptService {
    private final ScriptDAO scriptDAO;

    @Autowired
    public ScriptServiceImpl(ScriptDAO scriptDAO) {
        this.scriptDAO = scriptDAO;
    }

    @Override
    public ScriptVO getMyScript(Integer fileId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, String> params = new HashMap<>();
        params.put("fileId", fileId.toString());
        params.put("userId", currentUserId.toString());

        ScriptVO script = scriptDAO.getScript(params);
        if (script != null) {
            script.setScriptParagraphs(scriptDAO.getScriptParagraphs(params));
        }
        return script;
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
