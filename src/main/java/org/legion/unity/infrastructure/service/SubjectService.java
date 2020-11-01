package org.legion.unity.infrastructure.service;

import org.legion.unity.common.base.SearchParam;
import org.legion.unity.common.base.SearchResult;
import org.legion.unity.common.utils.PagingUtils;
import org.legion.unity.common.utils.SpecUtils;
import org.legion.unity.infrastructure.dao.SubjectDAO;
import org.legion.unity.infrastructure.entity.Subject;
import org.legion.unity.infrastructure.vo.SubjectVO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectService(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public List<Subject> getSubjectCategories() {
        return subjectDAO.findCategories();
    }

    public List<Subject> getSecondLevelSubjects(String topId) {
        return subjectDAO.findSecondLevelSubjects(topId);
    }

    public SearchResult<SubjectVO> searchThirdLevelSubjects(SearchParam param) {
        Specification<Subject> spec = (Specification<Subject>) (root, cq, cb) -> {
            List<Predicate> predicateList = SpecUtils.allEqual(root, cb, param.getParams());
            return cq.where(predicateList.toArray(new Predicate[0])).getRestriction();
        };
        Page<Subject> page = subjectDAO.findAll(spec, PagingUtils.paging(param));
        List<Subject> resultList = page.getContent();
        List<SubjectVO> voList = new ArrayList<>();
        for (Subject subject : resultList) {
            SubjectVO vo = new SubjectVO(subject);
            voList.add(vo);
        }
        SearchResult<SubjectVO> result = new SearchResult<>(param);
        result.setResultList(voList);
        result.setTotalCounts((int) page.getTotalElements());
        return result;
    }

}
