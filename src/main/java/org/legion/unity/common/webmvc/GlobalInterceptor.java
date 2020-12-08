package org.legion.unity.common.webmvc;

import org.legion.unity.common.base.AppContext;
import org.legion.unity.common.utils.ConfigUtils;
import org.legion.unity.common.utils.Redis;
import org.legion.unity.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object context = session.getAttribute(AppContext.APP_CONTEXT_KEY);
        if (context instanceof AppContext) {
            AppContext existContext = Redis.getJsonObject(((AppContext) context).getUserId(), AppContext.class);
            if (existContext != null && existContext.getSessionId().equals(((AppContext) context).getSessionId())) {
                return true;
            } else if (existContext != null) {
                request.getSession().invalidate();
                response.sendRedirect("/SessionExpired");
                log.info("User Session Duplicated -> " + ((AppContext) context).getUserId());
            } else {
                response.sendRedirect("/ea/login");
            }
        } else {
            if (!StringUtils.parseBoolean(ConfigUtils.get("server.appContext.enabled"))) {
                AppContext appContext = new AppContext();
                appContext.setUserId("DEV_VIRTUAL_ID");
                appContext.setLoggedIn(true);
                appContext.setAppContext(request);
                return true;
            } else {
                response.sendRedirect("/ea/login");
                log.warn("Intercepted request: " + request.getRequestURL());
            }
        }
        return false;
    }

}
