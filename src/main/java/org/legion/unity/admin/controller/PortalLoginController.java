package org.legion.unity.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.legion.unity.admin.dto.UserDTO;
import org.legion.unity.admin.entity.LoginStatus;
import org.legion.unity.admin.service.PortalLoginService;
import org.legion.unity.admin.service.UserService;
import org.legion.unity.common.base.AppContext;
import org.legion.unity.common.base.Response;
import org.legion.unity.common.base.ValidationResult;
import org.legion.unity.common.utils.DateUtils;
import org.legion.unity.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(tags = {"门户登录控制器"})
@RestController
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


    @ApiOperation(value = "登录验证", protocols = "application/json",
            httpMethod = "POST", response = Response.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/ea/login")
    public Response<ValidationResult> login(@RequestBody UserDTO user, HttpServletRequest request) throws Exception {
        Response<ValidationResult> res = new Response<>();
        ValidationResult vr = new ValidationResult();
        res.setData(vr);
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username)) {
            vr.addFailedField("username", "mandatory");
        } else if (StringUtils.isBlank(password)) {
            vr.addFailedField("password", "mandatory");
        } else {
            LoginStatus loginStatus = loginService.login(username, password, request);
            if (loginStatus == LoginStatus.SUCCESS) {
                AppContext context = AppContext.getAppContext(request);
                context.setLoggedIn(true);
                context.setSessionId(request.getSession().getId());

                /*if (AppConst.YES.equals(webUser.getIsFirstLogin())) {
                    responder.addDataObject("FirstLogin");
                    context.setLoggedIn(false);
                    SessionManager.setAttribute(SESSION_KEY, webUser);
                }*/
                log.info("User [" + username + "] Login Successfully at "
                        + DateUtils.getDateString(new Date(), DateUtils.FULL_STD_FORMAT_1));
            } else if (loginStatus == LoginStatus.ACCOUNT_EXPIRED) {
                vr.addFailedField("loginId", "账户已过期");
            } else if (loginStatus == LoginStatus.ACCOUNT_LOCKED) {
                vr.addFailedField("loginId", "账户已锁定");
            } else if (loginStatus == LoginStatus.ACCOUNT_INACTIVE) {
                vr.addFailedField("loginId", "账户尚未启用");
            } else if (loginStatus == LoginStatus.ACCOUNT_FROZEN) {
                vr.addFailedField("loginId", "账户已冻结");
            } else {
                vr.addFailedField("loginId", "用户名或密码不正确");
                vr.addFailedField("password", "用户名或密码不正确");
            }
        }
        return res;
    }

}


