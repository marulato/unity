package org.legion.unity.infrastructure.controller;

import org.legion.unity.common.base.AjaxResponseBody;
import org.legion.unity.common.base.AjaxResponseManager;
import org.legion.unity.common.base.SearchParam;
import org.legion.unity.common.base.SearchResult;
import org.legion.unity.common.consts.AppConst;
import org.legion.unity.infrastructure.entity.Subject;
import org.legion.unity.infrastructure.service.SubjectService;
import org.legion.unity.infrastructure.vo.SubjectVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/infra/subject")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("infrastructure/subject");
        modelAndView.addObject("role", "");
        modelAndView.addObject("category", subjectService.getSubjectCategories());
        return modelAndView;
    }

    @GetMapping("/infra/subject/level2")
    @ResponseBody
    public AjaxResponseBody toggleSecondLevel(HttpServletRequest request) {
        AjaxResponseManager manager = AjaxResponseManager.create(AppConst.RESPONSE_SUCCESS);
        manager.addDataObject(subjectService.getSecondLevelSubjects(request.getParameter("topId")));
        return manager.respond();
    }


    @PostMapping("/infra/subject/search")
    @ResponseBody
    public AjaxResponseBody searchThirdLevel(@RequestBody SearchParam param) {
        AjaxResponseManager manager = AjaxResponseManager.create(AppConst.RESPONSE_SUCCESS);
        param.setType(Subject.class);
        manager.addDataObject(subjectService.searchThirdLevelSubjects(param));
        return manager.respond();
    }
}
