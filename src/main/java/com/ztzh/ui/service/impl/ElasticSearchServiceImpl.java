package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
	public Page<MaterialInfoIndex> findDocument(int page, int size) {
		//QueryBuilder queryBuilder = QueryBuilders.termQuery("materialName", "lxk");
		SearchQuery searchQuery = getEntitySearchQuery(page,size,"lxk");
		Page<MaterialInfoIndex> items = materialInfoIndexRepository.search(searchQuery);
		return items;
	}
	
	private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        QueryBuilder functionScoreQueryBuilder = QueryBuilders.matchQuery("materialName", searchContent);

        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

}
