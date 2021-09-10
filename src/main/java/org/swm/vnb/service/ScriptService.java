package org.swm.vnb.service;

import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;

public interface ScriptService {
    ScriptVO getMyScript(Integer scriptId);
    void createScriptAndFile(ScriptVO script);
    void createParagraph(Integer scriptId, ScriptParagraphVO paragraph);
    void updateParagraph(Integer paragraphId, ScriptParagraphVO paragraph);
    void deleteParagraph(Integer paragraphId);
}
