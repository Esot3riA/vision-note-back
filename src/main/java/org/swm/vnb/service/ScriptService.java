package org.swm.vnb.service;

import org.swm.vnb.model.FullScriptVO;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;

public interface ScriptService {
    FullScriptVO getFullScript(Integer scriptId);
    void createScriptAndFile(ScriptVO script);
    void updateScriptRecording(Integer scriptId, Boolean isRecording);

    void createParagraph(Integer scriptId, ScriptParagraphVO paragraph);
    void updateParagraph(Integer paragraphId, ScriptParagraphVO paragraph);
    void deleteParagraph(Integer paragraphId);
}
