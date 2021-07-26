package org.swm.vnb.service;

import org.swm.vnb.model.UserVO;

public interface UserService {
    UserVO getUser(Integer id);
    void createUser(UserVO user);
    void updateUser(Integer id, UserVO user);
    void deleteUser(Integer id);
}
