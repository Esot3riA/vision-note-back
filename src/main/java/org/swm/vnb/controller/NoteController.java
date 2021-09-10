package org.swm.vnb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;
import org.swm.vnb.model.NoteItemVO;
import org.swm.vnb.service.NoteService;

import java.util.HashMap;
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

    @GetMapping("/note/folder/root")
    @ApiOperation(value="루트 폴더 조회", notes="현재 로그인 된 유저의 루트 폴더에 있는 노트 파일 및 폴더를 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getRootNoteItems() {
        return ResponseEntity.ok(noteService.getMyRootNoteInfo());
    }

    @GetMapping("/note/folder/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 조회", notes="요청한 폴더 하위의 노트 파일 및 폴더들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getNoteItems(@PathVariable Integer folderId) {
        List<NoteItemVO> noteItems = noteService.getMyNoteItems(folderId);

        return ResponseEntity.ok(noteItems);
    }

    @GetMapping("/note/search/{keyword}")
    @ApiOperation(value="노트 검색", notes="모든 노트를 검색한다. 유저의 모든 학습노트 중 keyword가 포함된 노트를 반환한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity searchNotes(@PathVariable String keyword) {
        List<HashMap<String, Object>> noteFiles = noteService.searchNotes(keyword);

        return ResponseEntity.ok(noteFiles);
    }

    @PutMapping("/note/file/{fileId:[0-9]+}")
    @ApiOperation(value="노트 수정", notes="노트 정보를 수정한다. 기존 노트 정보가 파라미터로 모두 대체된다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateNoteFile(@PathVariable Integer fileId, @ModelAttribute NoteFileVO noteFile) {
        noteService.updateNoteFile(fileId, noteFile);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/note/file/{fileId:[0-9]+}")
    @ApiOperation(value="노트 삭제", notes="노트를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteNoteFile(@PathVariable Integer fileId) {
        noteService.deleteNoteFile(fileId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/note/folder")
    @ApiOperation(value="폴더 생성", notes="새로운 노트 폴더를 생성한다.")
    @ApiResponses({
            @ApiResponse(code=201, message="생성 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createNoteFolder(@ModelAttribute NoteFolderVO noteFolder) {
        noteService.createNoteFolder(noteFolder);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/note/folder/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 수정", notes="노트 폴더 정보를 수정한다. 기존 노트 폴더 정보가 파라미터로 모두 대체된다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateNoteFolder(@PathVariable Integer folderId, @ModelAttribute NoteFolderVO noteFolder) {
        noteService.updateNoteFolder(folderId, noteFolder);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/note/folder/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 삭제", notes="노트 폴더를 삭제한다. 하위에 있는 모든 노트가 삭제된다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteNoteFolder(@PathVariable Integer folderId) {
        noteService.deleteNoteFolder(folderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
