package org.swm.vnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.swm.vnb.model.UserVO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swm.vnb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"유저 API"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity getAllUser() {
        List<UserVO> users = userService.getAllUser();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id:[0-9]+}")
    public ResponseEntity getUser(@PathVariable Integer id) {
        UserVO user = userService.getUser(id);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/user/{id:[0-9]+}")
    public ResponseEntity createUser(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/user/{id:[0-9]+}")
    public ResponseEntity updateUser(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/{id:[0-9]+}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
