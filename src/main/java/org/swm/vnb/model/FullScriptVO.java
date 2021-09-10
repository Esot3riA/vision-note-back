package org.swm.vnb.model;

import lombok.Data;

import java.util.List;

@Data
public class FullScriptVO {
    private ScriptVO script;
    private List<ScriptParagraphVO> scriptParagraphs;
}
