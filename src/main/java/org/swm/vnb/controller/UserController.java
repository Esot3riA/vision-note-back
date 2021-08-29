package org.swm.vnb.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Api(tags = {"유저 API"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @ApiOperation(value="내 정보 조회", notes="내 유저 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=403, message="조회 권한 없음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMyUserInfo() {
        return ResponseEntity.ok(userService.getUserByContext());
    }

    @GetMapping("/user/{id:[0-9]+}")
    @ApiOperation(value="특정 유저 정보 조회", notes="주어진 ID를 가진 유저의 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=403, message="조회 권한 없음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getUserInfo(@PathVariable Integer id) {
        UserVO user = userService.getUserById(id);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/user-types")
    @ApiOperation(value="유저 유형 조회", notes="모든 유저 유형을 조회한다. 인증 없이 호출할 수 있다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공")})
    public ResponseEntity getUserTypes() {
        List<UserTypeVO> userTypes = userService.getUserTypes();
        return new ResponseEntity(userTypes, HttpStatus.OK);
    }

    @PostMapping(value="/user")
    @ApiOperation(value="유저 등록", notes="새로운 유저를 등록한다. 인증 없이 호출할 수 있다.")
    @ApiResponses({
            @ApiResponse(code=200, message="등록 성공"),
            @ApiResponse(code=400, message="제출된 데이터 부족")})
    public ResponseEntity createUser(@ModelAttribute UserVO user) {
        userService.createUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/user/{id:[0-9]+}")
    @ApiOperation(value="유저 정보 수정", notes="이미 등록된 유저의 정보를 수정한다. 기존 유저 정보가 파라미터로 주어진 정보로 모두 대체된다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음"),
            @ApiResponse(code=403, message="수정 권한 없음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateUser(@PathVariable Integer id, @ModelAttribute UserVO user) {
        userService.updateUser(id, user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/{id:[0-9]+}")
    @ApiOperation(value="유저 삭제", notes="유저를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음"),
            @ApiResponse(code=403, message="삭제 권한 없음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
