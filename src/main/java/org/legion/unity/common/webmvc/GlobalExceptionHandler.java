package org.legion.unity.common.webmvc;

import org.legion.unity.common.ex.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView serverError(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof PermissionDeniedException) {
            return permissionDenied((PermissionDeniedException) e);
        }
        return new ModelAndView("errors/500");
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ModelAndView permissionDenied(PermissionDeniedException e) {
        //log.error(e.getMessage(), e);
        return new ModelAndView("errors/permissionDenied");
    }

}
