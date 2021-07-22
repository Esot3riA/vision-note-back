package org.swm.vnb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.swm.vnb.model.UserVO;

import java.util.List;

@Repository
@Mapper
public interface UserDAO {
    List<UserVO> getAllUser();
    UserVO getUser(Integer id);
}
