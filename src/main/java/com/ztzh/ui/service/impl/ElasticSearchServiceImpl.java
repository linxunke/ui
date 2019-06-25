package com.ztzh.ui.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.MaterialCountByParentType;
import com.ztzh.ui.bo.MaterialESBo;
import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.constants.MaterialTypeConstants;
import com.ztzh.ui.service.ElasticSearchService;
import com.ztzh.ui.service.MaterialHistoryCollectionService;
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
		logger.info("{}",searchQuery.getQuery());
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
        	boolQueryBuilder.should(QueryBuilders.fuzzyQuery("materialName", materialESBo.getMaterialName()));
        }
        if(null!=materialESBo.getMaterialDescription()&&""!=materialESBo.getMaterialDescription()) {
        	boolQueryBuilder.must(QueryBuilders.fuzzyQuery("materialDescription", materialESBo.getMaterialDescription()));
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
        	String materialTypeCodeParents = materialESBo.getMaterialTypeCodeParent();
        	List<String> materialTypeCodeParentList = Arrays.asList(materialTypeCodeParents.split(","));
        	for(String materialTypeCodeParent:materialTypeCodeParentList) {
        		boolQueryBuilder.should(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeParent", materialTypeCodeParent));
        	}
        }
        if(null!=materialESBo.getMaterialTypeCodeChild()&&""!=materialESBo.getMaterialTypeCodeChild()) {
        	String materialTypeCodeChilds = materialESBo.getMaterialTypeCodeChild();
        	List<String> materialTypeCodeChildList = Arrays.asList(materialTypeCodeChilds.split(","));
        	for(String materialTypeCodeChild:materialTypeCodeChildList) {
        		boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeChild", materialTypeCodeChild));
        	}
        }
        if(null!=materialESBo.getMaterialStyleCode()&&""!=materialESBo.getMaterialStyleCode()) {
        	//判断是否为父节点
        	if("01".equals(materialESBo.getMaterialStyleCode())) {
        		boolQueryBuilder.should(QueryBuilders.termQuery("materialTypeInfoIndex.materialStyleCode", materialESBo.getMaterialStyleCode()));
        	}else {
        		boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialStyleCode", materialESBo.getMaterialStyleCode()));
        	}
        	
        }
        RangeQueryBuilder rangeQueryBuider = QueryBuilders.rangeQuery("colorPercentage");
        if(null!=materialESBo.getColorPercentage()) {
        	rangeQueryBuider = rangeQueryBuider.gte(materialESBo.getColorPercentage()*0.97).lte(materialESBo.getColorPercentage()*1.03);
        }
        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        logger.info(searchBuilder.withPageable(pageable)
                .withQuery(boolQueryBuilder).withFilter(rangeQueryBuider).build().toString());
        return searchBuilder.withPageable(pageable)
                .withQuery(boolQueryBuilder).withFilter(rangeQueryBuider).build();
    }


	@Override
	public void deleteDocById(List<MaterialInfoIndex> materialInfoIndexList) {
		logger.info("开始根据条件删除Elasticsearch中的数据, materialInfoIndexList:{}",materialInfoIndexList);
		materialInfoIndexRepository.delete(materialInfoIndexList);
		logger.info("删除Elaticsearch数据成功");
		
	}


	@Override
	public List<MaterialCountByParentType> countMaterialByType(MaterialESBo materialESBo) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		NativeSearchQueryBuilder nativeSearchQuerybuilder = new NativeSearchQueryBuilder();
		RangeQueryBuilder rangeQueryBuider = QueryBuilders.rangeQuery("colorPercentage");
		 if(null!=materialESBo.getMaterialName()&&""!=materialESBo.getMaterialName()) {
	        	boolQueryBuilder.should(QueryBuilders.fuzzyQuery("materialName", materialESBo.getMaterialName()));
	        }
	        if(null!=materialESBo.getMaterialDescription()&&""!=materialESBo.getMaterialDescription()) {
	        	boolQueryBuilder.must(QueryBuilders.fuzzyQuery("materialDescription", materialESBo.getMaterialDescription()));
	        }
        if(null!=materialESBo.getColorPercentage()) {
        	rangeQueryBuider = rangeQueryBuider.gte(materialESBo.getColorPercentage()*0.97).lte(materialESBo.getColorPercentage()*1.03);
        	boolQueryBuilder.filter(rangeQueryBuider);
        }
		if(null!=materialESBo.getMaterialTypeCodeParent()) {
			String materialTypeCodeParents = materialESBo.getMaterialTypeCodeParent();
			List<String> materialTypeCodeParentList = Arrays.asList(materialTypeCodeParents.split(","));
			for(String materialTypeCodeParent:materialTypeCodeParentList){
				boolQueryBuilder.should(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeParent", materialTypeCodeParent));
			}
		}
		if(null!=materialESBo.getMaterialTypeCodeChild()&&""!=materialESBo.getMaterialTypeCodeChild()) {
	        String materialTypeCodeChilds = materialESBo.getMaterialTypeCodeChild();
	        List<String> materialTypeCodeChildList = Arrays.asList(materialTypeCodeChilds.split(","));
	        for(String materialTypeCodeChild:materialTypeCodeChildList) {
	        	boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialTypeCodeChild", materialTypeCodeChild));
	        }
	    }
		if(null!=materialESBo.getMaterialStyleCode()&&""!=materialESBo.getMaterialStyleCode()) {
			if("01".equals(materialESBo.getMaterialStyleCode())) {
        		boolQueryBuilder.should(QueryBuilders.termQuery("materialTypeInfoIndex.materialStyleCode", materialESBo.getMaterialStyleCode()));
        	}else {
        		boolQueryBuilder.must(QueryBuilders.termQuery("materialTypeInfoIndex.materialStyleCode", materialESBo.getMaterialStyleCode()));
        	}
	    }
		if(null!=materialESBo.getColorType()) {
        	boolQueryBuilder.must(QueryBuilders.termQuery("colorType", materialESBo.getColorType()));
        }
		nativeSearchQuerybuilder.withQuery(boolQueryBuilder);
		nativeSearchQuerybuilder.withSearchType(SearchType.QUERY_THEN_FETCH);
		nativeSearchQuerybuilder.withIndices("materialinfo").withTypes("docs");
		TermsBuilder termsAggregation = AggregationBuilders.terms("countMaterialByTypes").field("materialTypeInfoIndex.materialTypeCodeParent");
		nativeSearchQuerybuilder.addAggregation(termsAggregation);
		NativeSearchQuery nativeSearchQuery = nativeSearchQuerybuilder.build();
		logger.info("{}",nativeSearchQuery.getQuery());
		Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		Map<String, Aggregation> aggregationMap = aggregations.asMap();
		StringTerms stringTerms = (StringTerms) aggregationMap.get("countMaterialByTypes");
    	//获得所有的桶
    	List<Bucket> buckets = stringTerms.getBuckets();
    	List<MaterialCountByParentType> materialCountByParentTypeList = new ArrayList<MaterialCountByParentType>();
    	for(Bucket bucket:buckets){	
    		MaterialCountByParentType materialCountByParentType = new MaterialCountByParentType();
    		materialCountByParentType.setMaterialTypeCodeParent(bucket.getKeyAsString());
    		materialCountByParentType.setMaterialCount(bucket.getDocCount());
    		materialCountByParentTypeList.add(materialCountByParentType);
    	}	
    	return materialCountByParentTypeList;
	}

}
