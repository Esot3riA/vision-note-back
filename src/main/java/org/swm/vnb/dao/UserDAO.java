package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.UserTypeVO;
import org.swm.vnb.model.UserVO;

import java.util.List;

@Repository
@Mapper
public interface UserDAO {
    UserVO getUser(Integer id);
    List<UserTypeVO> getUserTypes();
    void createUser(UserVO user);
    void updateUser(UserVO user);
    void deleteUser(Integer id);
}
