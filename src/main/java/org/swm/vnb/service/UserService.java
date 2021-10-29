package org.swm.vnb.service;

import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;

import java.util.List;

public interface UserService {
    UserVO getUserById(Integer id);
    UserVO getUserByEmail(String email);
    Integer getUserIdByEmail(String email);
    UserVO getUserByContext();
    List<UserTypeVO> getUserTypes();
    void createUser(UserVO user);
    boolean updateMyInfo(UserVO user);
    void deleteMyAccount();
}
