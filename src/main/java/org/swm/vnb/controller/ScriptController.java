package org.swm.vnb.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.model.FullScriptVO;
import org.swm.vnb.model.ScriptParagraphVO;
import org.swm.vnb.model.ScriptVO;
import org.swm.vnb.service.ScriptService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/v1")
@Api(tags = {"스크립트 API"})
public class ScriptController {

    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping("/script/{scriptId:[0-9]+}")
    @ApiOperation(value="스크립트 조회", notes="주어진 script ID를 가진 스크립트의 정보와 문단들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getScript(@PathVariable Integer scriptId) {
        FullScriptVO fullScript = scriptService.getFullScript(scriptId);

        return ResponseEntity.ok(fullScript);
    }

    @PostMapping("/script")
    @ApiOperation(value="스크립트 생성", notes="새로운 학습 스크립트를 생성한다.")
    @ApiResponses({
            @ApiResponse(code=201, message="생성 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createScript(@ModelAttribute ScriptVO script) {
        scriptService.createScriptAndFile(script);

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("scriptId", script.getScriptId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseObj.toString());
    }

    @PostMapping("/script/recording/{scriptId:[0-9]+}")
    @ApiOperation(value="스크립트 녹음 여부 변경", notes="주어진 script ID의 녹음 중(isRecording) 여부를 변경한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="isRecording", dataType="int", paramType="query", required=true, example="0", allowableValues="0, 1")})
    @ApiResponses({
            @ApiResponse(code=204, message="수정 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateScriptRecording(@PathVariable Integer scriptId, @RequestParam Integer isRecording) {
        scriptService.updateScriptRecording(scriptId, isRecording);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/script/paragraph/{scriptId:[0-9]+}")
    @ApiOperation(value="스크립트 문단 생성", notes="주어진 script ID에 새 문단을 추가한다.")
    @ApiResponses({
            @ApiResponse(code=201, message="생성 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createParagraph(@PathVariable Integer scriptId, @ModelAttribute ScriptParagraphVO paragraph) {
        scriptService.createParagraph(scriptId, paragraph);

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("paragraphId", paragraph.getParagraphId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseObj.toString());
    }

    @PutMapping("/script/paragraph/{paragraphId:[0-9]+}")
    @ApiOperation(value="스크립트 문단 수정", notes="주어진 paragraph ID에 해당하는 문단 정보를 수정한다. 파라미터로 제공된 요소들만 수정된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime", dataType="String", paramType="query"),
            @ApiImplicitParam(name="endTime", dataType="String", paramType="query"),
            @ApiImplicitParam(name="paragraphContent", dataType="String", paramType="query"),
            @ApiImplicitParam(name="memoContent", dataType="String", paramType="query"),
            @ApiImplicitParam(name="isBookmarked", dataType="int", paramType="query", example="0")})
    @ApiResponses({
            @ApiResponse(code=204, message="수정 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateParagraph(@PathVariable Integer paragraphId,
                                          @ApiIgnore @ModelAttribute ScriptParagraphVO paragraph) {
        scriptService.updateParagraph(paragraphId, paragraph);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/script/paragraph/{paragraphId:[0-9]+}")
    @ApiOperation(value="스크립트 문단 삭제", notes="문단을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code=201, message="수정 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteParagraph(@PathVariable Integer paragraphId) {
        scriptService.deleteParagraph(paragraphId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
