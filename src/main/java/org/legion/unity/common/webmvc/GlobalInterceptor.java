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

        return false;
    }

}
