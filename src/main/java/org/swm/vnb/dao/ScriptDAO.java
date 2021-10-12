package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ScriptDAO {
    ScriptVO getScript(Map<String, String> params);
    void createScript(ScriptVO script);
    void updateScriptRecording(Map<String, Object> params);

    List<ScriptParagraphVO> getScriptParagraphs(Map<String, String> params);
    void createParagraph(ScriptParagraphVO paragraph);
    void updateParagraph(ScriptParagraphVO paragraph);
    void deleteParagraph(Map<String, String> params);

    void deleteScript(Map<String, Object> params);
    void deleteScriptsByUserId(Integer userId);

    void deleteKeywordsByScriptId(Map<String, Object> params);
    void deleteKeywordsByUserId(Integer userId);

    void deleteParagraphsByScriptId(Map<String, Object> params);
    void deleteParagraphsByUserId(Integer userId);
}
