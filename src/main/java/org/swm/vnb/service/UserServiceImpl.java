package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserVO;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserVO getUser(Integer id) {
        return userDAO.getUser(id);
    }

    public void createUser(UserVO user) {
        userDAO.createUser(user);
    }

    public void updateUser(Integer id, UserVO user)  {
        user.setUser_id(id);
        userDAO.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userDAO.deleteUser(id);
    }

}
