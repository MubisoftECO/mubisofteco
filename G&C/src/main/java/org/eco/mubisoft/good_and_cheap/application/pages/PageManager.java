package org.eco.mubisoft.good_and_cheap.application.pages;

public class PageManager {

    private PageManager(){}

    public static Integer getPageNum(Integer pageNum, Integer totalPageCount, String direction) {
        if (pageNum != null && direction != null) {
            if (direction.equals("next")) {
                if (totalPageCount > pageNum) {
                    pageNum++;
                } else if (totalPageCount < pageNum) {
                    pageNum = totalPageCount;
                }
            } else {
                if (pageNum > 1) {
                    pageNum--;
                }
            }
        } else {
            pageNum = 1;
        }
        return pageNum;
    }

}
