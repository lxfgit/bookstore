package com.book.lxf.web;

import java.util.List;

/**
 * Created by LXF on 2015/11/19.
 */
public class Page <T>{
    //当前第几页
    private int pageNo;

    //当前页的List
    private List<T> list;

    //每一页显示多少条记录
    private int pageSize = 5;

    //总共有多少条记录
    private long totalItemNumber;

    //构造器需要对pageNo初始化
    public Page(int pageNo) {
        this.pageNo = pageNo;
    }

    //需要校验一下
    public int getPageNo() {
        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageNo > getTotalPageNumber()) {
            pageNo = getTotalPageNumber();
        }
        return pageNo;
    }

    public int getPageSiz() {
        return pageSize;
    }

    public void setPageSiz(int pageSiz) {
        this.pageSize = pageSiz;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    //获取总页数
    public int getTotalPageNumber() {
        int totalPageNumber = (int)totalItemNumber / pageSize;
        if (totalPageNumber % pageSize != 0) {
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public void setTotalItemNumber(long totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
    }


    public boolean isHasNext() {
        if (getPageNo() < getTotalPageNumber()) {
            return true;
        }
        return false;
    }

    public boolean isHasPre() {
        if (getPageNo() > 1) {
            return true;
        }
        return false;
    }

    public int getPrePage() {
        if (isHasPre()) {
            return getPageNo() - 1;
        }
        return getPageNo();
    }

    public int getNextPage() {
        if (isHasNext()) {
            return getPageNo() + 1;
        }
        return getPageNo();
    }
}
