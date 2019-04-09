package com.ztzh.ui.utils;

import java.util.List;

public class PageQueryUtil {
	public static final int PAGE_SIZE_IS_6 = 6;
	public static final int PAGE_SIZE_IS_10 = 10;
	public static final int PAGE_SIZE_IS_20 = 20;
	public static final int PAGE_SIZE_IS_30 = 30;
	public static final int PAGE_SIZE_IS_40 = 40;
	public static final int PAGE_SIZE_IS_50 = 50;
	
	private int pageSize;
	private int pageNumber;
	private int currentPage = 1;
	private int infoTotalNumber;
	private Object object;
	
	public PageQueryUtil() {
		super();
	}
	
	public PageQueryUtil(int pageSize, int currentPage,int infoTotalNumber) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.infoTotalNumber = infoTotalNumber;
		//根据pagesize,currentPage来确定总页数pageNumber
		this.pageNumber = (this.infoTotalNumber % this.pageSize) == 0 ?
				(this.infoTotalNumber / this.pageSize):(this.infoTotalNumber / this.pageSize)+1;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getInfoTotalNumber() {
		return infoTotalNumber;
	}
	public void setInfoTotalNumber(int infoTotalNumber) {
		this.infoTotalNumber = infoTotalNumber;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "PageQueryUtil [pageSize=" + pageSize + ", pageNumber="
				+ pageNumber + ", currentPage=" + currentPage
				+ ", infoTotalNumber=" + infoTotalNumber + ", object=" + object
				+ "]";
	}
}
