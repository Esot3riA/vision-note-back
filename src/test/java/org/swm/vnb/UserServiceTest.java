package org.swm.vnb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swm.vnb.dao.NoteDAO;
import org.swm.vnb.dao.ScriptDAO;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserVO;
import org.swm.vnb.service.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserDAO userDAO;
    @Mock NoteDAO noteDAO;
    @Mock ScriptDAO scriptDAO;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void createAndVerifyUser() {
        // given
        UserVO user = UserVO.builder()
                .email("esot6ria@gmail.com")
                .password("1q2w3e")
                .nickname("eso")
                .avatar("avatar.png")
                .socialType("NORMAL")
                .build();

        userService.createUser(user);
        when(userDAO.getUserByEmail("esot6ria@gmail.com")).thenReturn(user);

        // when
        UserVO result = userService.getUserByEmail("esot6ria@gmail.com");

        // then
        assertThat(result, equalTo(user));
    }
}
