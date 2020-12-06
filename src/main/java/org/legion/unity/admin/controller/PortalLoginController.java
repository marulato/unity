package org.legion.unity.admin.controller;

import org.legion.unity.admin.entity.LoginStatus;
import org.legion.unity.admin.entity.User;
import org.legion.unity.admin.service.PortalLoginService;
import org.legion.unity.admin.service.UserService;
import org.legion.unity.common.base.AppContext;
import org.legion.unity.common.base.Responder;
import org.legion.unity.common.base.Response;
import org.legion.unity.common.base.SessionManager;
import org.legion.unity.common.consts.AppConst;
import org.legion.unity.common.utils.DateUtils;
import org.legion.unity.common.utils.LogUtils;
import org.legion.unity.common.validation.CommonValidator;
import org.legion.unity.common.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class PortalLoginController {

    private final UserService userService;
    private final PortalLoginService loginService;
    private static final String SESSION_KEY = "SESSION_USER";

    private static final Logger log = LoggerFactory.getLogger(PortalLoginController.class);

    @Autowired
    public PortalLoginController(UserService userService, PortalLoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * Web View Call
     * @return Login page
     */
    @GetMapping("/ueo/login")
    public String getLoginPage() {
        log.info(LogUtils.around("Enter login page"));
        return "admin/login";
    }

    /**
     * Ajax Call
     * @return login validation result
     */
    @PostMapping("/ueo/login")
    @ResponseBody
    public Response login(User webUser, HttpServletRequest request) throws Exception {
        Responder responder = Responder.ready();
        List<ConstraintViolation> violations = CommonValidator.validate(webUser, null);
        if (!violations.isEmpty()) {
            responder.addValidations(violations);
        } else {
            LoginStatus loginStatus = loginService.login(webUser, request);
            if (loginStatus == LoginStatus.SUCCESS) {
                AppContext context = AppContext.getAppContext(request);
                context.setLoggedIn(true);
                context.setSessionId(request.getSession().getId());
                loginService.checkInSession(request);
                responder.addDataObject(0);
                if (AppConst.YES.equals(webUser.getIsFirstLogin())) {
                    responder.addDataObject("FirstLogin");
                    context.setLoggedIn(false);
                    SessionManager.setAttribute(SESSION_KEY, webUser);
                }
                log.info("User [" + webUser.getId() + "] Login Successfully at "
                        + DateUtils.getDateString(new Date(), DateUtils.FULL_STD_FORMAT_1));
            } else if (loginStatus == LoginStatus.ACCOUNT_EXPIRED) {
                responder.addError("loginId", "账户已过期");
            } else if (loginStatus == LoginStatus.ACCOUNT_LOCKED) {
                responder.addError("loginId", "账户已锁定");
            } else if (loginStatus == LoginStatus.ACCOUNT_INACTIVE) {
                responder.addError("loginId", "账户尚未启用");
            } else if (loginStatus == LoginStatus.ACCOUNT_FROZEN) {
                responder.addError("loginId", "账户已冻结");
            } else {
                responder.addError("loginId", "");
                responder.addError("password", "用户名或密码不正确");
            }
        }
        return responder.ok();
    }

}


