package com.ztzh.ui.utils;

import com.ztzh.ui.dao.CanvasInfoDomainMapper;

public class QueryByPage {

	CanvasInfoDomainMapper canvasInfoDomainMapper;
	public int pageSize = 8;
	//分页查询画板
	public int getCanvasTotalPage(Long userid) {	
		//1.查询总条数，调用noteDao里的方法获取
		int totalCount = canvasInfoDomainMapper.selectCountByUserId(userid);
		//2.计算总页数
		int pageCount=totalCount%pageSize == 0 ? totalCount/pageSize:(totalCount/pageSize+1);
		//3.返回出去
		return pageCount;
	}
	

}
