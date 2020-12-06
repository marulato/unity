package org.legion.unity.common.webmvc;

import org.legion.unity.common.utils.ConfigUtils;
import org.legion.unity.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.List;

@Configuration
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
        registry.addInterceptor(globalInterceptor).addPathPatterns("/ueo/**").excludePathPatterns(excludeList);
    }

}
