package com.ztzh.ui.vo;

import java.util.List;

import com.ztzh.ui.bo.MaterialInfoIndex;

public class MaterialInfoIndexVo {
	private int totalPage;
	private int currentPage;
	private List<MaterialInfoIndex> items;
	
	
	public MaterialInfoIndexVo() {
		super();
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<MaterialInfoIndex> getItems() {
		return items;
	}
	public void setItems(List<MaterialInfoIndex> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "MaterialInfoIndexVo [totalPage=" + totalPage + ", currentPage=" + currentPage + ", items=" + items
				+ "]";
	}

}
