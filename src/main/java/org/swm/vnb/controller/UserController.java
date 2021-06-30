package org.swm.vnb.controller;

import org.swm.vnb.model.UserVO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"유저 API"})
public class UserController {

    @GetMapping("/user/{id:[0-9]+}")
    public ResponseEntity getUser(@PathVariable Integer id) {
        UserVO user = new UserVO();
        user.setId(id);
        user.setEmail("esot3ria@gmail.com");
        user.setName("Esot3riA");
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
