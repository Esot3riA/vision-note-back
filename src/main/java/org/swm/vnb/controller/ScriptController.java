package org.swm.vnb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swm.vnb.model.ScriptVO;
import org.swm.vnb.service.ScriptService;

@RestController
@RequestMapping("/v1")
@Api(tags = {"스크립트 API"})
public class ScriptController {

    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping("/script/{id:[0-9]+}")
    @ApiOperation(value="스크립트 조회", notes="주어진 ID를 가진 스크립트의 정보와 문단들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=403, message="조회 권한 없음")})
    public ResponseEntity getScript(@PathVariable Integer id) {
        ScriptVO script = scriptService.getScript(id);
        return new ResponseEntity(script, HttpStatus.OK);
    }

}
