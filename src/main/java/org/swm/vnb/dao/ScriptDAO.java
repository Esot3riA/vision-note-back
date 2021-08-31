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
    List<ScriptParagraphVO> getScriptParagraphs(Map<String, String> params);

    void deleteScriptsByUserId(Integer userId);
    void deleteKeywordsByUserId(Integer userId);
    void deleteParagraphsByUserId(Integer userId);
}
