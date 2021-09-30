package org.swm.vnb.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.model.NoteFileVO;
import org.swm.vnb.model.NoteFolderVO;
import org.swm.vnb.model.NoteItemVO;
import org.swm.vnb.service.NoteService;
import springfox.documentation.annotations.ApiIgnore;

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

    @GetMapping("/note/folder/childs/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 하위 항목 조회", notes="요청한 폴더 하위의 노트 파일 및 폴더들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getNoteItems(@PathVariable Integer folderId) {
        List<NoteItemVO> noteItems = noteService.getNoteItems(folderId);

        return ResponseEntity.ok(noteItems);
    }

    @GetMapping("/note/folder/parents/{folderId:[0-9]+}")
    @ApiOperation(value="상위 폴더 일괄 조회", notes="요청한 폴더 상위에 있는 폴더들을 조회한다. 하위 폴더부터 상위 폴더까지 순서대로 반환된다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getAllParentFolders(@PathVariable Integer folderId) {
        List<NoteFolderVO> parentFolders = noteService.getAllParentFolders(folderId);

        return ResponseEntity.ok(parentFolders);
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
    @ApiOperation(value="노트 수정", notes="노트 정보를 수정한다. 파라미터로 제공된 요소들만 수정된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="folderId", dataType="int", paramType="query", example="1"),
            @ApiImplicitParam(name="fileName", dataType="String", paramType="query"),
            @ApiImplicitParam(name="isImportant", dataType="int", paramType="query", example="0")})
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateNoteFile(@PathVariable Integer fileId,
                                         @ApiIgnore @ModelAttribute NoteFileVO noteFile) {
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
        noteService.deleteNoteFileAndScript(fileId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/note/folder/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 조회", notes="노트 폴더의 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getNoteFolder(@PathVariable Integer folderId) {
        NoteFolderVO noteFolder = noteService.getNoteFolder(folderId);

        return ResponseEntity.ok(noteFolder);
    }

    @PostMapping("/note/folder")
    @ApiOperation(value="폴더 생성", notes="새로운 노트 폴더를 생성한다.")
    @ApiResponses({
            @ApiResponse(code=201, message="생성 성공"),
            @ApiResponse(code=400, message="유효한 부모 폴더가 아님"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createNoteFolder(@ModelAttribute NoteFolderVO noteFolder) {
        if (!noteService.isValidFolderId(noteFolder.getParentFolderId())) {
            return ResponseEntity.badRequest().body(null);
        }

        noteService.createNoteFolder(noteFolder);

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("folderId", noteFolder.getFolderId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseObj.toString());
    }

    @PutMapping("/note/folder/{folderId:[0-9]+}")
    @ApiOperation(value="폴더 수정", notes="노트 폴더 정보를 수정한다. 파라미터로 제공된 요소들만 수정된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="folderName", dataType="String", paramType="query"),
            @ApiImplicitParam(name="parentFolderId", dataType="int", paramType="query", example="1")})
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateNoteFolder(@PathVariable Integer folderId,
                                           @ApiIgnore @ModelAttribute NoteFolderVO noteFolder) {
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
