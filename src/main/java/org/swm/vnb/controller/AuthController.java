package org.swm.vnb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swm.vnb.jwt.JwtFilter;
import org.swm.vnb.jwt.TokenProvider;
import org.swm.vnb.model.TokenVO;
import org.swm.vnb.model.UserVO;
import org.swm.vnb.service.UserService;

@RestController
@RequestMapping("/v1")
@Api(tags = {"인증 API"})
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    public AuthController(TokenProvider tokenProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder,
                          UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    @ApiOperation(value="유저 인증", notes="유저를 인증하고 토큰을 발급한다.")
    @ApiResponses({
            @ApiResponse(code=200, message="인증 성공"),
            @ApiResponse(code=401, message="유저 정보 없음")})
    public ResponseEntity authenticate(@ModelAttribute UserVO user) {

        Integer userId = userService.getUserIdByEmail(user.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, user.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new TokenVO(jwt), httpHeaders, HttpStatus.OK);
    }
}
