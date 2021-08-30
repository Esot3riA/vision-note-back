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
    @ApiOperation(value="내 정보 조회", notes="현재 로그인 된 유저 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMyInfo() {
        return ResponseEntity.ok(userService.getUserByContext());
    }

    @GetMapping("/user-types")
    @ApiOperation(value="유저 유형 조회", notes="모든 유저 유형을 조회한다. 인증 없이 호출할 수 있다.")
    @ApiResponses({
            @ApiResponse(code=200, message="조회 성공")})
    public ResponseEntity getUserTypes() {
        List<UserTypeVO> userTypes = userService.getUserTypes();

        return ResponseEntity.ok(userTypes);
    }

    @PostMapping(value="/user")
    @ApiOperation(value="유저 등록", notes="새로운 유저를 등록한다. 인증 없이 호출할 수 있다.")
    @ApiResponses({
            @ApiResponse(code=200, message="등록 성공"),
            @ApiResponse(code=400, message="제출된 데이터 부족"),
            @ApiResponse(code=409, message="이메일 중복")})
    public ResponseEntity createUser(@ModelAttribute UserVO user) {
        try {
            userService.createUser(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/user")
    @ApiOperation(value="내 정보 수정", notes="현재 유저의 정보를 수정한다. 기존 유저 정보가 파라미터로 주어진 정보로 모두 대체된다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateMyInfo(@ModelAttribute UserVO user) {
        userService.updateMyInfo(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/user")
    @ApiOperation(value="내 계정 삭제", notes="현재 로그인 된 유저 계정을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteMyAccount() {
        userService.deleteMe();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
