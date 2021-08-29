package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserVO getUserById(Integer id) {
        return userDAO.getUserById(id);
    }

    @Override
    public UserVO getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public List<UserTypeVO> getUserTypes() {
        return userDAO.getUserTypes();
    }

    @Override
    public void createUser(UserVO user) {
        userDAO.createUser(user);
    }

    @Override
    public void updateUser(Integer id, UserVO user)  {
        user.setUser_id(id);
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userDAO.deleteUser(id);
    }

}
