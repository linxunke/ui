package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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
import com.ztzh.ui.bo.MaterialESBo;
import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.constants.MaterialTypeConstants;
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
	public Page<MaterialInfoIndex> findMaterialDocument(int page, int size, MaterialESBo materialESBo) {
		SearchQuery searchQuery = getEntitySearchQuery(page,size,materialESBo);
		Page<MaterialInfoIndex> items = materialInfoIndexRepository.search(searchQuery);
		return items;
	}
	
	/*
	 * 拼接materialindex查询条件 
	 */
	private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, MaterialESBo materialESBo) {
		NativeSearchQueryBuilder searchBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(null!=materialESBo.getMaterialName()&&""!=materialESBo.getMaterialName()) {
        	boolQueryBuilder.must(QueryBuilders.matchQuery("materialName", materialESBo.getMaterialName()));
        }
        if(null!=materialESBo.getMaterialDescription()&&""!=materialESBo.getMaterialDescription()) {
        	boolQueryBuilder.must(QueryBuilders.matchQuery("materialDescription", materialESBo.getMaterialDescription()));
        }
        if(null!=materialESBo.getSort()&&""!=materialESBo.getSort()) {
        	if(materialESBo.getSort().equals(MaterialTypeConstants.MATERIAL_TYPE_HOT_CODE)) {
        		searchBuilder.withSort(SortBuilders.fieldSort("countDownload")
                        .order(SortOrder.DESC));
        	}else if(materialESBo.getSort().equals(MaterialTypeConstants.MATERIAL_TYPE_NEW_CODE)){
        		searchBuilder.withSort(SortBuilders.fieldSort("uploadTime")
                        .order(SortOrder.DESC));
        	}
        }
        if(null!=materialESBo.getColorType()) {
        	boolQueryBuilder.must(QueryBuilders.termQuery("colorType", materialESBo.getColorType()));
        }
        if(null!=materialESBo.getMaterialTypeCodeParent()&&""!=materialESBo.getMaterialTypeCodeParent()) {
        	boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeParent", materialESBo.getMaterialTypeCodeParent()));
        }
        if(null!=materialESBo.getMaterialTypeCodeChild()&&""!=materialESBo.getMaterialTypeCodeChild()) {
        	boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeChild", materialESBo.getMaterialTypeCodeChild()));
        }
        if(null!=materialESBo.getMaterialStyleCode()&&""!=materialESBo.getMaterialStyleCode()) {
        	boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialStyleCode", materialESBo.getMaterialStyleCode()));
        }
        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return searchBuilder.withPageable(pageable)
                .withQuery(boolQueryBuilder).build();
    }

}
