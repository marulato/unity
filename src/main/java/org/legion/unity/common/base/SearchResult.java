package org.legion.unity.common.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  SearchResult <E> implements Serializable {

    private Integer totalCounts;
    private Integer draw;
    private List<E> resultList;

    public SearchResult() {
        resultList = new ArrayList<>();
    }

    public SearchResult(List<E> resultList, SearchParam param) {
        this.resultList = new ArrayList<>();
        if (resultList != null) {
            this.resultList.addAll(resultList);
            draw = param.getDraw();
        }
    }

    public SearchResult(Page<E> page, SearchParam param) {
        if (page != null && param != null) {
            resultList = page.getContent();
            totalCounts = (int) page.getTotalElements();
            draw = param.getDraw();
        }
    }

    public SearchResult(SearchParam param) {
        if (param != null) {
            draw = param.getDraw();
        }
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
        if (resultList != null) {
            this.totalCounts = resultList.size();
        }
    }
}
