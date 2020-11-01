package org.legion.unity.admin.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.legion.unity.admin.dao.UserDAO;
import org.legion.unity.admin.entity.User;
import org.legion.unity.common.utils.DateUtils;
import org.legion.unity.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO UserDAO;

    @Autowired
    public UserService(UserDAO UserDAO) {
        this.UserDAO = UserDAO;

    }

    public String encryptPassword(String pwd) {
        if (StringUtils.isNotBlank(pwd)) {
            String hash = pwd;
            for (int i = 0; i < 5; i++) {
                hash = DigestUtils.sha256Hex(hash);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(hash);
        }
        return pwd;
    }

    public boolean isPasswordMatch(String plaintext, String ciphertext) {
        if (StringUtils.isNotEmpty(plaintext) && StringUtils.isNotEmpty(ciphertext)) {
            String hash = plaintext;
            for (int i = 0; i < 5; i++) {
                hash = DigestUtils.sha256Hex(hash);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(hash, ciphertext);
        }
        return false;
    }

    public boolean isActive(User user) {
        if (user != null) {
            long now = DateUtils.now().getTime();
            return user.getActivatedAt().getTime() <= now && user.getDeactivatedAt().getTime() >= now;
        }
        return false;
    }

    public User getUserByLoginId(String loginId) {
        if (StringUtils.isNotBlank(loginId)) {
            return UserDAO.findById(loginId).orElse(null);
        }
        return null;
    }


}
