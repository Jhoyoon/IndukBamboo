package com.instorage.myproject.domain;

public class PageHandler {
    private int totalCnt; // 총 게시물 개수
    private int pageSize; // 페이지의 크기
    private int naviSize = 10; // 페이지 네비게이션 크기
    private int totalPage; // 전체 페이즈의 수
    private int page; // 현재 페이지
    private int beginPage; // 시작페이지
    private int endPage; // 끝 페이지
    private boolean showPrev; // 이전 페이지를 보여줄지 여부 결정
    private boolean showNext; // 다음 페이지를 보여줄지 여부 결정


    public PageHandler(int totalCnt, int page,int pageSize) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;
        if(totalCnt%pageSize == 0) this.totalPage = totalCnt/pageSize;
        else this.totalPage = totalCnt/pageSize +1;
        this.beginPage = (page -1) / this.naviSize * this.naviSize + 1;

        endPage = Math.min(beginPage+9,totalPage);

        if(beginPage == 1) showPrev = false;
        else showPrev=true;

        if(endPage == totalPage) showNext = false;
        else showNext = true;
        
//        this.totalCnt = totalCnt;
//        this.page = page;
//        this.pageSize = pageSize;
//        // beginpage 설정
//        if(page%10 == 0) beginPage=page-9;
//        else  beginPage = page/10*10+1;
//        // 전체 페이지수 설정.Math.ceil에 결과가 실수로 들어가야 하기에 (double)
//        totalPage = (int)Math.ceil((double)totalCnt/10);
//        // 끝 페이지 설정
//        endPage = Math.min(beginPage+9,totalPage);
//        // 앞으로 뒤로를 보여줄지 말지 결정하는 설정
//        showPrev = beginPage!=1;
//        showNext = endPage!=totalPage;
//
//        if(showPrev) System.out.print("<< ");
//        for(int i = beginPage;i<=endPage;i++){
//            if(page == i) System.out.print("["+i+"]");
//            else System.out.print(i);
//        }
//        if(showNext) System.out.println(">>");
//        else System.out.println();
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int gettotalPage() {
        return totalPage;
    }

    public void settotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
