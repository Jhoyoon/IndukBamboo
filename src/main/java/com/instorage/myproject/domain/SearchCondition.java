package com.instorage.myproject.domain;

import java.util.Objects;

public class SearchCondition {
    private String type;
    private String option = "";
    private String keyword = "";
    private Integer page;
    private Integer pageSize;
    private Integer offset;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCondition that = (SearchCondition) o;
        return Objects.equals(page, that.page) && Objects.equals(pageSize, that.pageSize) && Objects.equals(offset, that.offset) && Objects.equals(keyword, that.keyword) && Objects.equals(option, that.option);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize, offset, keyword, option);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (page-1)*pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public SearchCondition(){}
//    public SearchCondition(String option,String keyword){
//        this.option = option;
//        this.keyword = keyword;
//        SearchCondition(option,keyword,page,pageSize,offset);
//    }

    public SearchCondition(String option,String keyword,Integer page, Integer pageSize) {
        this.option = option;
        this.keyword = keyword;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "option='" + option + '\'' +
                ", keyword='" + keyword + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                '}';
    }
}
