package com.book.lxf.web;

/**
 * Created by LXF on 2015/11/19.
 */
public class CriteriaBook {

    private float minPrice = 0;
    private float maxPrive = Integer.MAX_VALUE;

    private int pageNo;

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrive() {
        return maxPrive;
    }

    public void setMaxPrive(float maxPrive) {
        this.maxPrive = maxPrive;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public CriteriaBook(float minPrice, float maxPrive, int pageNo) {
        this.minPrice = minPrice;
        this.maxPrive = maxPrive;
        this.pageNo = pageNo;
    }
}
