package org.swm.vnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.swm.vnb.dao.UserDAO;
import org.swm.vnb.model.UserVO;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserVO user = userDAO.getUserById(Integer.parseInt(userId));
        if (user == null) {
            throw new UsernameNotFoundException("User " + userId + " is not exist.");
        }
        return user;
    }
}
