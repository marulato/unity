package org.legion.unity.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import org.legion.unity.common.utils.StringUtils;
import org.legion.unity.common.validation.CommonValidator;
import org.legion.unity.common.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    @ApiOperation(value = "登录验证", protocols = "http: application/json;charset=utf-8", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "输入的用户名", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "输入的密码", required = true, paramType = "body")
    })
    @PostMapping("/ea/login")
    public Response login(@RequestBody Map<String, String> auth, HttpServletRequest request) throws Exception {
        Responder responder = Responder.ready();
        String username = auth.get("username");
        String password = auth.get("password");
        if (StringUtils.isBlank(username)) {
            responder.addError("username", "mandatory");
        } else if (StringUtils.isBlank(password)) {
            responder.addError("password", "mandatory");
        } else {
            LoginStatus loginStatus = loginService.login(username, password, request);
            if (loginStatus == LoginStatus.SUCCESS) {
                AppContext context = AppContext.getAppContext(request);
                context.setLoggedIn(true);
                context.setSessionId(request.getSession().getId());
                responder.addDataObject(0);
                /*if (AppConst.YES.equals(webUser.getIsFirstLogin())) {
                    responder.addDataObject("FirstLogin");
                    context.setLoggedIn(false);
                    SessionManager.setAttribute(SESSION_KEY, webUser);
                }*/
                log.info("User [" + username + "] Login Successfully at "
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


