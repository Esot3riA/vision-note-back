package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.NoteFolderVO;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;
import org.swm.vnb.util.SecurityUtil;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final NoteDAO noteDAO;
    private final ScriptDAO scriptDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, NoteDAO noteDAO, ScriptDAO scriptDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.noteDAO = noteDAO;
        this.scriptDAO = scriptDAO;
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
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserVO user) throws RuntimeException {
        if (userDAO.getUserByEmail(user.getEmail()).getUserId() != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        user.setAuthority("ROLE_USER");
        user.setAvatar("avatar.svg");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userDAO.createUser(user);

        NoteFolderVO rootFolder = new NoteFolderVO();
        rootFolder.setUserId(user.getUserId());
        rootFolder.setFolderName("내 노트");
        rootFolder.setParentFolderId(null);

        noteDAO.createNoteFolder(rootFolder);
    }

    @Override
    public boolean updateMyInfo(UserVO user)  {
        if (user == null || !user.hasPracticalValues()) {
            return false;
        }
        if (!isEmptyPassword(user.getPassword()) && !isValidPassword(user.getPassword())) {
            return false;
        }

        user.setUserId(SecurityUtil.getCurrentUserId());
        user.setPassword(encodePassword(user.getPassword()));
        userDAO.updateUser(user);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAccount() {
        Integer currentUserId = SecurityUtil.getCurrentUserId();

        scriptDAO.deleteKeywordsByUserId(currentUserId);
        scriptDAO.deleteParagraphsByUserId(currentUserId);
        scriptDAO.deleteScriptsByUserId(currentUserId);

        noteDAO.deleteNoteFilesByUserId(currentUserId);
        noteDAO.deleteNoteFoldersByUserId(currentUserId);

        userDAO.deleteUser(currentUserId);
    }

    private boolean isEmptyPassword(String password) {
        return password == null || password == "";
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%_*?&~#\\\"'()+,-./:;<=>@])[A-Za-z\\d$@$!%*_?&~#\\\"'()+,-./:;<=>@]{8,20}";
        return Pattern.matches(pattern, password);
    }

    private String encodePassword(String password) {
        if (StringUtils.hasText(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

}
