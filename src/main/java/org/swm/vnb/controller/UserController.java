package org.swm.vnb.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.swm.vnb.model.FileVO;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.service.UserService;
import org.swm.vnb.util.FileSaveUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Api(tags = {"유저 API"})
public class UserController {

    private final UserService userService;
    private final FileSaveUtil fileSaveUtil;

    @Autowired
    public UserController(UserService userService, FileSaveUtil fileSaveUtil) {
        this.userService = userService;
        this.fileSaveUtil = fileSaveUtil;
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
            @ApiResponse(code=201, message="등록 성공"),
            @ApiResponse(code=400, message="제출 파라미터 이상"),
            @ApiResponse(code=409, message="이메일 중복")})
    public ResponseEntity createUser(@Valid @ModelAttribute UserVO user, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            userService.createUser(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/user")
    @ApiOperation(value="내 정보 수정", notes="현재 유저의 정보를 수정한다. 파라미터로 제공된 요소들만 수정된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="typeId", dataType="int", paramType="query", example="1"),
            @ApiImplicitParam(name="password", dataType="String", paramType="query"),
            @ApiImplicitParam(name="nickname", dataType="String", paramType="query"),
            @ApiImplicitParam(name="avatar", dataType="String", paramType="query"),
            @ApiImplicitParam(name="socialType", dataType="String", paramType="query")})
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=400, message="적합한 데이터가 아님"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateMyInfo(@ApiIgnore @ModelAttribute UserVO user) {
        if (userService.updateMyInfo(user)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/user")
    @ApiOperation(value="내 계정 삭제", notes="현재 로그인 된 유저 계정을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteMyAccount() {
        userService.deleteMyAccount();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/user/find-password")
    @ApiOperation(value="비밀번호 찾기", notes="유저 비밀번호를 초기화하고 유저 메일로 초기화 된 비밀번호 정보를 전송한다. 닉네임과 이메일 정보가 유효해야 한다.")
    @ApiResponses({
            @ApiResponse(code=204, message="표시 정보 없음"),
            @ApiResponse(code=400, message="유효한 유저 정보가 아님")})
    public ResponseEntity findMyPassword(@RequestParam String email, @RequestParam String nickname) {

        UserVO user = userService.getUserByEmail(email);
        if (user == null || !StringUtils.equals(user.getNickname(), nickname)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String randomPassword = RandomStringUtils.randomAlphanumeric(16);
        userService.resetPassword(email, randomPassword);

        return ResponseEntity.ok(null);
    }

    @PutMapping("/user/avatar")
    @ApiOperation(value="유저 아바타 변경", notes="현재 로그인 된 유저의 아바타 이미지를 변경한다. 기존 아바타 이미지는 삭제된다.")
    @ApiResponses({
            @ApiResponse(code=201, message="변경 완료"),
            @ApiResponse(code=401, message="로그인되지 않음")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateMyAvatar(@RequestParam("profile") MultipartFile avatar) {
        UserVO user = userService.getUserByContext();
        if (user.getAvatar() != "avatar.svg") {
            fileSaveUtil.deleteImage(user.getAvatar());
        }

        if (!isValidImage(avatar)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a valid image");
        }
        try {
            FileVO newAvatarInfo = fileSaveUtil.saveImage(avatar);

            UserVO userVO = UserVO.builder().avatar(newAvatarInfo.getSavedName()).build();
            userService.updateMyInfo(userVO);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("avatarName", newAvatarInfo.getSavedName());

            return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save image", e);
        }
    }

    private boolean isValidImage(MultipartFile image) {
        final long mb = 1024 * 1024;
        return (image.getSize() <= (mb * 50)) && (image.getContentType().startsWith("image"));
    }

}
