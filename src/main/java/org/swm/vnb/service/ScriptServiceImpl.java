package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.ScriptDAO;
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
    public ScriptVO getMyScript(Integer scriptId) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        Map<String, String> params = new HashMap<>();
        params.put("scriptId", scriptId.toString());
        params.put("userId", currentUserId.toString());

        ScriptVO script = scriptDAO.getScript(params);
        if (script != null) {
            script.setScriptParagraphs(scriptDAO.getScriptParagraphs(params));
        }
        return script;
    }
}
