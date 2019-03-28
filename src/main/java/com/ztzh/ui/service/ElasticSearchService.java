package com.ztzh.ui.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import com.ztzh.ui.bo.MaterialInfoIndex;

public interface ElasticSearchService {
	public<T> boolean createIndex(Class<T> index);
	
	public boolean deleteIndex(String indexName);
	
	public void saveDocument(List docList);
	
	/**
	 * 根据条件搜索
	 * @param queryBuilder 查询条件
	 * @param object 返回的类型
	 * @param page 起始页
	 * @param size 每页大小
	 * @return
	 */
	public Page<MaterialInfoIndex> findDocument(NativeSearchQueryBuilder queryBuilder, int page, int size);

}
