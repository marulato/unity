package org.legion.unity.common.webmvc;

import org.legion.unity.common.utils.ConfigUtils;
import org.legion.unity.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.List;

public class WebMvcConfig implements WebMvcConfigurer {

    private final GlobalInterceptor globalInterceptor;

    @Autowired
    public WebMvcConfig(GlobalInterceptor globalInterceptor) {
        this.globalInterceptor = globalInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String excludePatterns = ConfigUtils.get("spring.mvc.interceptor.excludePatterns");
        List<String> excludeList = new ArrayList<>();
        if (StringUtils.isNotBlank(excludePatterns)) {
            String[] patterns = excludePatterns.split(",");
            for (String pattern : patterns) {
                excludeList.add(pattern.trim());
            }
        }
        excludeList.add("/dist/**");
        excludeList.add("/plugins/**");
        excludeList.add("/build/**");
        excludeList.add("/js/**");
        excludeList.add("/css/**");
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
    }

}
