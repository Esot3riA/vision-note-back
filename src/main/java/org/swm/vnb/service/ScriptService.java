package org.swm.vnb.service;

import org.swm.vnb.model.FullScriptVO;
import org.swm.vnb.model.ScriptParagraphKeywordVO;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;

public interface ScriptService {
    FullScriptVO getFullScript(Integer scriptId);
    ScriptVO getScript(Integer scriptId);
    Integer getScriptIdByParagraphId(Integer paragraphId);

    void createScriptAndFile(ScriptVO script);
    void updateScriptRecording(Integer scriptId, Integer isRecording);
    void updateScriptAudio(Integer scriptId, Integer audioId);

    void createParagraph(Integer scriptId, ScriptParagraphVO paragraph);
    void updateParagraph(Integer paragraphId, ScriptParagraphVO paragraph);
    void deleteParagraph(Integer paragraphId);

    ScriptParagraphKeywordVO createParagraphKeyword(Integer paragraphId, String keyword);
    void deleteParagraphKeyword(Integer paragraphId, String keyword);
}
