package com.ztzh.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.MaterialAndTypeInfoBo;
import com.ztzh.ui.bo.MaterialChildTypeCoverInfosBo;
import com.ztzh.ui.dao.CanvasInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.service.MaterialInfoService;
import com.ztzh.ui.service.MaterialLibraryService;
import com.ztzh.ui.utils.PageQueryUtil;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping("/materialLibrary")
public class MaterialLibraryController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	MaterialLibraryService materialLibraryService;

	@Autowired
	MaterialInfoService materialInfoService;

	@Autowired
	CanvasInfoDomainMapper canvasInfoDomainMapper;

	@Autowired
	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;

	@Autowired
	MaterialTypeDomainMapper materialTypeDomainMapper;

	@RequestMapping(value = "/getAllMaterialParentTypeInfo", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getAllMaterialParentTypeInfo(
			@RequestParam(value = "userId", required = true) String userId) {
		ResponseVo responseVo = new ResponseVo();
		List<MaterialTypeDomain> parentTypeList = materialLibraryService
				.getAllMaterialParentType();
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setUserId(userId);
		if (parentTypeList.size() != 0) {
			responseVo.setMessage("成功获取一级分类信息");
			responseVo.setObject(parentTypeList);
		} else {
			responseVo.setMessage("获取一级分类信息失败，分类列表为空");
		}
		return responseVo.toString();
	}

	@RequestMapping(value = "/getChildTypesByParentTypeCode", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getChildTypesByParentTypeCode(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "parentTypeCode", required = true) String parentTypeCode,
			@RequestParam(value = "isIcon", required = true) boolean isIcon) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		List<MaterialChildTypeCoverInfosBo> resultList = materialLibraryService
				.getChildTypeInfoByParentCode(parentTypeCode, isIcon);
		responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		responseVo.setMessage("根据分类获取细分类别的素材信息成功");
		responseVo.setObject(resultList);
		/* 执行service层的方法 */
		return responseVo.toString();
	}

	@RequestMapping(value = "/getMaterialsByChildTypeCode", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getMaterialsByChildTypeCode(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "childTypeCode", required = true) String childTypeCode,
			@RequestParam(value = "currentPage", required = true) int currentPage) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		PageQueryUtil pageQueryUtil;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if ("01".equalsIgnoreCase(materialTypeDomainMapper
				.selectParentTypeCodeByChildTypeCode(childTypeCode))) {
			pageQueryUtil = materialLibraryService.getMaterialsByChildTypeCode(
					childTypeCode, currentPage, PageQueryUtil.PAGE_SIZE_IS_40);
			resultMap.put("isIcon", true);
		} else {
			pageQueryUtil = materialLibraryService.getMaterialsByChildTypeCode(
					childTypeCode, currentPage, PageQueryUtil.PAGE_SIZE_IS_6);
			resultMap.put("isIcon", false);
		}
		if (pageQueryUtil.getInfoTotalNumber() != 0) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("根据细分类型获取素材信息成功");
		} else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("根据细分类型获取到的素材列表为空");
		}
		resultMap.put("pageInfoUtil", pageQueryUtil);
		responseVo.setObject(resultMap);
		return responseVo.toString();
	}

	@RequestMapping(value = "/getMaterialInfoInLibrary", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getMaterialInfoInLibrary(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "materialId", required = true) String materialId) {
		ResponseVo responseVo = new ResponseVo();
		MaterialAndTypeInfoBo materialBo = materialInfoService
				.getMaterialDetailInfoById(new Long(materialId));
		CanvasInfoDomain canvasInfo = canvasInfoDomainMapper
				.selectByPrimaryKey(materialBo.getCanvasInfoIdPrivate());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (materialBo != null && canvasInfo != null) {
			resultMap.put("materialInfo", materialBo);
			resultMap.put("canvasInfo", canvasInfo);
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取素材库中素材的详细信息成功");
		} else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取素材库中素材的详细信息为空");
		}
		responseVo.setUserId(userId);
		responseVo.setObject(resultMap);
		return responseVo.toString();
	}

	@RequestMapping(value = "/getCurrentTypeInfos", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String getTypeNameInfos(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "childTypeCode", required = true) String childTypeCode) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId);
		List<MaterialTypeDomain> resultList = materialLibraryService
				.getMaterialTypeInfoByChildTypeCode(childTypeCode);
		if (resultList.size() != 0) {
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
			responseVo.setMessage("获取当前分类的类别和细分信息成功");
		} else {
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
			responseVo.setMessage("获取当前分类的类别和细分信息列表为空");
		}
		responseVo.setObject(resultList);
		return responseVo.toString();
	}
}
