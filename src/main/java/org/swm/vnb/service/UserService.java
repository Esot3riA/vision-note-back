package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserVO;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserVO> getAllUser() {
        return userDAO.getAllUser();
    }

    public UserVO getUser(Integer id) {
        return userDAO.getUser(id);
    }
}
