package org.legion.unity.admin.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.legion.unity.admin.dao.RoleAssignDAO;
import org.legion.unity.admin.dao.RoleDAO;
import org.legion.unity.admin.dao.UserDAO;
import org.legion.unity.admin.entity.User;
import org.legion.unity.admin.entity.UserRole;
import org.legion.unity.admin.entity.UserRoleAssign;
import org.legion.unity.common.utils.DateUtils;
import org.legion.unity.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO UserDAO;
    private final RoleDAO roleDAO;
    private final RoleAssignDAO roleAssignDAO;

    @Autowired
    public UserService(UserDAO UserDAO, RoleDAO roleDAO, RoleAssignDAO roleAssignDAO) {
        this.UserDAO = UserDAO;
        this.roleDAO = roleDAO;
        this.roleAssignDAO = roleAssignDAO;
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
            return UserDAO.findById(loginId);
        }
        return null;
    }

    public UserRole getRoleById(String id) {
        return roleDAO.findById(id);
    }

    public UserRoleAssign getUserRoleAssignByUserId(String userId) {
        return roleAssignDAO.findByUserId(userId);
    }


}
