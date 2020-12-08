package org.legion.unity.admin.service;

import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.net.util.Base64;
import org.legion.unity.admin.entity.*;
import org.legion.unity.common.base.AppContext;
import org.legion.unity.common.base.SessionManager;
import org.legion.unity.common.consts.AppConst;
import org.legion.unity.common.ex.RepeatLoginException;
import org.legion.unity.common.jpa.exec.DAO;
import org.legion.unity.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PortalLoginService {

    private final UserService userService;


    @Autowired
    public PortalLoginService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginStatus login(User webUser, HttpServletRequest request) {
        LoginStatus status = null;
        if (webUser != null) {
            User user = userService.getUserByLoginId(webUser.getId());
            if (user != null) {
                AppContext appContext = new AppContext();
                appContext.setUserId(user.getId());
                appContext.setDomain(user.getDomain());
                appContext.setAppContext(request);
                String accountStatus = checkStatus(user);
                if (AppConst.ACCOUNT_STATUS_ACTIVE.equals(accountStatus)) {
                    boolean isPwdMatch = userService.isPasswordMatch(webUser.getPassword(), user.getPassword());
                    if (isPwdMatch) {
                        status = LoginStatus.SUCCESS;
                        UserRoleAssign userRoleAssign = userService.getUserRoleAssignByUserId(user.getId());
                        if (userRoleAssign != null) {
                            UserRole role = userService.getRoleById(userRoleAssign.getRoleId());
                            appContext.setRole(role);
                            appContext.setName(user.getName());
                            webUser.setIsFirstLogin(user.getIsFirstLogin());
                        } else {
                            status = LoginStatus.ACCOUNT_EXPIRED;
                        }
                    } else {
                        status = LoginStatus.INVALID_PASS;
                    }
                } else if (AppConst.ACCOUNT_STATUS_INACTIVE.equals(accountStatus)) {
                    status = LoginStatus.ACCOUNT_INACTIVE;
                } else if (AppConst.ACCOUNT_STATUS_EXPIRED.equals(accountStatus)) {
                    status = LoginStatus.ACCOUNT_EXPIRED;
                } else if (AppConst.ACCOUNT_STATUS_LOCKED.equals(accountStatus)) {
                    status = LoginStatus.ACCOUNT_LOCKED;
                } else if (AppConst.ACCOUNT_STATUS_FROZEN.equals(accountStatus)) {
                    status = LoginStatus.ACCOUNT_FROZEN;
                } else if (AppConst.ACCOUNT_STATUS_VOIDED.equals(accountStatus)) {
                    status = LoginStatus.VOIDED;
                }
                logLogin(user, status, request);
            } else {
                AppContext.createVirtualContext("unknown",false, request);
                status = LoginStatus.ACCOUNT_NOT_EXIST;
                UnknownLoginHistory unHistory = new UnknownLoginHistory();
                if (webUser.getId().length() > 32) {
                    webUser.setId(webUser.getId().substring(0, 32));
                }
                Date now = new Date();
                unHistory.setUserId(webUser.getId());
                unHistory.setIpAddress(SessionManager.getIpAddress(request));
                unHistory.setLoginAt(now);
                unHistory.setBrowser(request.getParameter("browser"));
                DAO.save(unHistory);
            }
        }
        return status;
    }


    public void logLogin(User user, LoginStatus status, HttpServletRequest request) {
        if (user != null && status != null) {
            user.setLastLoginAttemptDt(DateUtils.now());
            user.setLastLoginIp(user.getLastLoginIp());
            if (status == LoginStatus.SUCCESS) {
                user.setLastLoginIp(SessionManager.getIpAddress(request));
                user.setLastLoginSuccessDt(user.getLastLoginAttemptDt());
                user.setLoginFailedTimes(0);
                //user.setIsFirstLogin(AppConst.NO);
            } else if (status == LoginStatus.INVALID_PASS) {
                user.setLoginFailedTimes(user.getLoginFailedTimes() + 1);
                Integer maxFailedTimes = 0;
                String maxFailedTimesStr = ConfigUtils.get("security.login.maxFailedTimes");
                if (StringUtils.isBlank(maxFailedTimesStr) || !StringUtils.isInteger(maxFailedTimesStr)
                        || Integer.parseInt(maxFailedTimesStr) <= 1 ) {
                    maxFailedTimes = 10;
                } else {
                    maxFailedTimes = StringUtils.parseIfIsInteger(maxFailedTimesStr);
                }
                if (maxFailedTimes != null && user.getLoginFailedTimes() >= maxFailedTimes) {
                    user.setStatus(AppConst.ACCOUNT_STATUS_LOCKED);
                }
            } else {
                user.setLoginFailedTimes(user.getLoginFailedTimes() + 1);
            }
            UserLoginHistory loginHistory = new UserLoginHistory();
            loginHistory.setUserId(user.getId());
            loginHistory.setAcctStatus(user.getStatus());
            loginHistory.setIpAddress(user.getLastLoginIp());
            loginHistory.setLoginAt(user.getLastLoginAttemptDt());
            loginHistory.setLoginStatus(status.getValue());
            loginHistory.setIpAddress(SessionManager.getIpAddress(request));
            loginHistory.setBrowser(request.getParameter("browser"));
            DAO.save(loginHistory);
            DAO.update(user);
        }
    }

    public void checkInSession(HttpServletRequest request) throws Exception {
        AppContext context = AppContext.getAppContext(request);
        if (context != null) {
            AppContext concurrentContext = Redis.getJsonObject(context.getUserId(), AppContext.class);
            if (concurrentContext != null && !concurrentContext.getSessionId().equals(context.getSessionId())) {
                Redis.putAsJson(context.getUserId(), context);
            } else if (concurrentContext == null) {
                Redis.putAsJson(context.getUserId(), context);
            } else {
                throw new RepeatLoginException();
            }
        }
    }

    public void checkOutSession(HttpServletRequest request) {
        AppContext context = AppContext.getAppContext(request);
        if (context != null) {
            Redis.delete(context.getUserId());
        }
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(user.getName())
                .setIssuer("UNITY c.n.o")
                .setExpiration(DateUtils.addMinutes(now, 60))
                .signWith(SignatureAlgorithm.HS512,
                        Base64.encodeBase64String(ConfigUtils.get("security.jwt.secretKey").getBytes(StandardCharsets.UTF_8)));
        return jwtBuilder.compact();
    }


    private String checkStatus(User user) {
        if (!userService.isActive(user)) {
            user.setStatus(AppConst.ACCOUNT_STATUS_EXPIRED);
        }
        if (userService.isActive(user) && AppConst.ACCOUNT_STATUS_INACTIVE.equals(user.getStatus())) {
            user.setStatus(AppConst.ACCOUNT_STATUS_ACTIVE);
        }
        if (AppConst.ACCOUNT_STATUS_LOCKED.equals(user.getStatus()) &&
                DateUtils.getHoursBetween(user.getLastLoginAttemptDt(), DateUtils.now()) >= 24) {
            user.setStatus(userService.isActive(user) ?
                    AppConst.ACCOUNT_STATUS_ACTIVE : AppConst.ACCOUNT_STATUS_EXPIRED);
        }
        return user.getStatus();
    }
}
