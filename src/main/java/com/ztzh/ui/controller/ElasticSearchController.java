package com.ztzh.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ztzh.ui.bo.MaterialCountByParentType;
import com.ztzh.ui.bo.MaterialESBo;
import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.constants.MaterialTypeConstants;
import com.ztzh.ui.service.ElasticSearchService;
import com.ztzh.ui.service.UploadMaterialsService;
import com.ztzh.ui.vo.MaterialInfoIndexVo;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "elasticsearch")
public class ElasticSearchController {
	Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);
	
	@Autowired
	ElasticSearchService elasticSearchService;
	
	@Autowired
	UploadMaterialsService uploadMaterialsService;
	
	@RequestMapping(value = "/createMaterialInfoIndex", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public String createMaterialInfoIndex() {
		elasticSearchService.deleteIndex("materialinfo");
		boolean result = elasticSearchService.createIndex(MaterialInfoIndex.class);
		ResponseVo responseVo = new ResponseVo();
		if(result) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("创建索引成功");
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("创建索引失败");
		}
		return responseVo.toString();
	}
	
	@RequestMapping(value="putAllMaterialInElasticsearch", method = {RequestMethod.GET,
			RequestMethod.POST}, produces = "application/json;charset=UTF-8")
	public String putAllMaterialInElasticsearch() {
		logger.info("开始添加doc");
		ResponseVo responseVo = new ResponseVo();
		boolean result = uploadMaterialsService.putAllMaterialInfoInElasticsearch();
		if(result) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("源文件已全部放入到搜索引擎中");
		}else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("源文件放入到搜索引擎中失败");
		}
		return responseVo.toString();
	}
	
	@RequestMapping(value="queryByParam", method = {RequestMethod.GET,
			RequestMethod.POST})
	public String queryByParam(@RequestParam Long userId,
			MaterialESBo materialESBo,@RequestParam int page, @RequestParam int pageSize) {
		logger.info("查询条件{}",materialESBo);
		Page<MaterialInfoIndex> items = elasticSearchService.findMaterialDocument(page, pageSize, materialESBo);
		MaterialInfoIndexVo materialInfoIndexVo = new MaterialInfoIndexVo();
		materialInfoIndexVo.setItems(items.getContent());
		materialInfoIndexVo.setTotalPage(pageSize);
		materialInfoIndexVo.setCurrentPage(page);
		return JSONObject.toJSON(materialInfoIndexVo).toString();
	}
	
	@RequestMapping(value="countMaterialByType", method = {RequestMethod.GET,
			RequestMethod.POST})
	public String countMaterialByType(@RequestParam Long userId,
			MaterialESBo materialESBo) {
		logger.info("查询条件{}",materialESBo);
		List<MaterialCountByParentType> materialCountByParentTypeList = elasticSearchService.countMaterialByType(materialESBo);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setMessage("查询成功");
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setObject(materialCountByParentTypeList);
		return responseVo.toString();
	}
	

}
