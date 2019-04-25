package com.ztzh.ui.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.dao.MaterialHistoryCollectionDomainMapper;
import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.service.MaterialHistoryCollectionService;
import com.ztzh.ui.vo.ResponseVo;

@RestController
@RequestMapping(value = "historyandcollection")
public class HistoryAndCollectionController {
	Logger logger = LoggerFactory
			.getLogger(HistoryAndCollectionController.class);

	@Autowired
	MaterialHistoryCollectionService materialHistoryCollectionService;

	@Autowired
	MaterialHistoryCollectionDomainMapper materialHistoryCollectionDomainMapper;

	@RequestMapping(value = "collectOperate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String collectOperate(@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "materialInfoId") Long materialInfoId,
			@RequestParam(value = "operationCode") String operationCode,
			@RequestParam(value = "type") Integer type) {
		logger.info("收藏操作条件是：{}", userId + "+" + materialInfoId + "+"
				+ operationCode + "+" + type);
		MaterialHistoryCollectionDomain materialHistoryCollectionDomain = new MaterialHistoryCollectionDomain();
		materialHistoryCollectionDomain.setMaterialInfoId(materialInfoId);
		materialHistoryCollectionDomain.setOperateTime(new Date());
		materialHistoryCollectionDomain.setType(type);
		materialHistoryCollectionDomain.setUserInfoId(userId);
		boolean isOperated = false;
		try {
			isOperated = materialHistoryCollectionService.collectMaterial(
					materialHistoryCollectionDomain, operationCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId.toString());
		if (isOperated) {
			responseVo.setMessage("操作成功");
			responseVo.setStatus(ResponseVo.STATUS_SUCCESS);
		} else {
			responseVo.setMessage("操作失败");
			responseVo.setStatus(ResponseVo.STATUS_FAILED);
		}
		return responseVo.toString();
	}

	@RequestMapping(value = "selectHistoryCount", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String selectHistoryCount(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "type") Integer type) {
		int historyCount = materialHistoryCollectionDomainMapper.countByUserId(
				userId, type);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId.toString());
		responseVo.setObject(historyCount);
		return responseVo.toString();
	}

	@RequestMapping(value = "selectHistoryOrCollectionMaterial", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String selectHistoryOrCollectionMaterial(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "type") Integer type,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "currentPage") int currentPage) {
		// 1.查询总条数，调用noteDao里的方法获取
		int historyCount = materialHistoryCollectionDomainMapper.countByUserId(userId, type);
		// 2.计算总页数
		int pageCount = historyCount % pageSize == 0 ? historyCount / pageSize
				: (historyCount / pageSize + 1);
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		//查出此人已收藏的素材id
		List<Long> list = materialHistoryCollectionService.SelectByUserInfoId(userId);
		//查出此人下载的素材信息
		List<MaterialInfoIndex> listMaterialInfo = 
				materialHistoryCollectionService.SelectByUserIdForHistory(userId, type);
				
		ResponseVo responseVo = new ResponseVo();
		responseVo.setUserId(userId.toString());
		responseVo.setObject(listMaterialInfo);
		return responseVo.toString();
	}
}
