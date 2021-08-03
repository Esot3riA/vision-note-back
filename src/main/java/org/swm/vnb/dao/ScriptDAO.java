package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;

import java.util.List;

@Repository
@Mapper
public interface ScriptDAO {
    ScriptVO getScript(Integer scriptId);
    List<ScriptParagraphVO> getScriptParagraphs(Integer scriptId);
}
