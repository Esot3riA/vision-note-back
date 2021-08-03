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
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteItemVO;
import org.swm.vnb.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Api(tags = {"학습노트 API"})
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 조회", notes="요청한 폴더 하위의 노트 파일 및 폴더들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=403, message="조회 권한 없음")})
    public ResponseEntity getNoteItems(@PathVariable Integer folderId) {
        List<NoteItemVO> noteItems = noteService.getNoteItems(folderId);
        return new ResponseEntity(noteItems, HttpStatus.OK);
    }

    @GetMapping("/note/search/{keyword}")
    @ApiOperation(value="노트 검색", notes="학습노트 제목을 검색한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="등록 성공"),
            @ApiResponse(code=403, message="조회 권한 없음")})
    public ResponseEntity searchNotes(@PathVariable String keyword) {
        List<NoteFileVO> noteFiles = noteService.searchNotes(keyword);
        return new ResponseEntity(noteFiles, HttpStatus.OK);
    }


}
