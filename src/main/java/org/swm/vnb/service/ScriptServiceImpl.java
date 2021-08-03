package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.model.ScriptVO;

@Service
public class ScriptServiceImpl implements ScriptService {
    private final ScriptDAO scriptDAO;

    @Autowired
    public ScriptServiceImpl(ScriptDAO scriptDAO) {
        this.scriptDAO = scriptDAO;
    }

    @Override
    public ScriptVO getScript(Integer scriptId) {
        ScriptVO script = scriptDAO.getScript(scriptId);
        script.setScriptParagraphs(scriptDAO.getScriptParagraphs(scriptId));
        return script;
    }
}
