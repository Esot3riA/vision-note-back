package org.swm.vnb.service;

import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;

import java.util.List;

public interface UserService {
    UserVO getUser(Integer id);
    List<UserTypeVO> getUserTypes();
    void createUser(UserVO user);
    void updateUser(Integer id, UserVO user);
    void deleteUser(Integer id);
}
