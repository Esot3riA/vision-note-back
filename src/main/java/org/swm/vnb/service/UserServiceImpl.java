package org.swm.vnb.service;

import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;
import org.swm.vnb.util.SecurityUtil;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
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
    public Integer getUserIdByEmail(String email) {
        return userDAO.getUserIdByEmail(email);
    }

    public UserVO getUserByContext() {
        return getUserById(SecurityUtil.getCurrentUserId());
    }

    @Override
    public List<UserTypeVO> getUserTypes() {
        return userDAO.getUserTypes();
    }

    @Override
    public void createUser(UserVO user) throws RuntimeException {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        user.setAuthority("ROLE_USER");
        user.setAvatar("avatar.png");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userDAO.createUser(user);
    }

    @Override
    public void updateMyInfo(UserVO user)  {
        user.setUser_id(SecurityUtil.getCurrentUserId());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userDAO.updateUser(user);
    }

    @Override
    public void deleteMe() {
        userDAO.deleteUser(SecurityUtil.getCurrentUserId());
    }

}
