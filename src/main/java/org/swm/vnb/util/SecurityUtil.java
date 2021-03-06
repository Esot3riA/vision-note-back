package org.swm.vnb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {}

    public static Integer getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return null;
        }

        String id = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            id = springSecurityUser.getUsername();
        }  else if (authentication.getPrincipal() instanceof String) {
            id = (String) authentication.getPrincipal();
        }

        return Integer.parseInt(id);
    }

    public static Map<String, Object> getUserParams() {
        Integer currentUserId = getCurrentUserId();

        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId.toString());
        return params;
    }

}
