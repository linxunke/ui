package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.service.ElasticSearchService;
import com.ztzh.ui.service.MaterialInfoIndexRepository;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
	Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@SuppressWarnings("rawtypes")
	
	@Autowired
	private MaterialInfoIndexRepository materialInfoIndexRepository;


	@Override
	public<T> boolean createIndex(Class<T> clazz) {
		boolean isCreateIndex = elasticsearchTemplate.createIndex(clazz);
		boolean isCreateMapping = false;
		if(isCreateIndex) {
			isCreateMapping = elasticsearchTemplate.putMapping(clazz);
			logger.info("是否完成索引创建:{}",isCreateMapping);
		}else {
			logger.info("索引创建失败。。。");
		}
		return isCreateMapping;
	}


	@Override
	public boolean deleteIndex(String indexName) {
		boolean isDeleteIndex = elasticsearchTemplate.deleteIndex(indexName);
		if(isDeleteIndex) {
			logger.info("删除{}索引成功",indexName);
		}else {
			logger.info("删除索引失败");
		}
		return isDeleteIndex;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void saveDocument(List docList) {
		materialInfoIndexRepository.save(docList);
	}


	@Override
	public Page<MaterialInfoIndex> findDocument(NativeSearchQueryBuilder queryBuilder, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		queryBuilder.withPageable(pageRequest);
		Page<MaterialInfoIndex> items = materialInfoIndexRepository.search(queryBuilder.build());
		return items;
	}

}
