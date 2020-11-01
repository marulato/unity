package org.legion.unity.admin.controller;

import org.legion.unity.admin.entity.User;
import org.legion.unity.admin.service.UserService;
import org.legion.unity.common.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PortalLoginController {

    private final UserService accountService;
    private static final String SESSION_KEY = "SESSION_USER";

    private static final Logger log = LoggerFactory.getLogger(PortalLoginController.class);

    @Autowired
    public PortalLoginController(UserService accountService) {
        this.accountService = accountService;
    }

    /**
     * Web View Call
     * @return Login page
     */
    @GetMapping("/ueo/login")
    public String getLoginPage() {
        log.info(LogUtils.around("Enter login page"));
        User user = accountService.getUserByLoginId("sys");
        System.out.println(user.getDisplayName() + ":" + user.getEmail());
        return "admin/login";
    }

}
