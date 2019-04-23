package com.ztzh.ui.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ztzh.ui.bo.MaterialChildTypeCoverInfosBo;
import com.ztzh.ui.bo.MaterialDetailsInfoBo;
import com.ztzh.ui.constants.MaterialTypeConstants;
import com.ztzh.ui.controller.UserController;
import com.ztzh.ui.dao.MaterialInfoDomainMapper;
import com.ztzh.ui.dao.MaterialTypeDomainMapper;
import com.ztzh.ui.dao.MaterialTypeInfoDomainMapper;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;
import com.ztzh.ui.service.MaterialLibraryService;
import com.ztzh.ui.utils.ImageMagickUtil;
import com.ztzh.ui.utils.PageQueryUtil;

@Service
public class MaterialLibraryServiceImpl implements MaterialLibraryService {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	MaterialInfoDomainMapper materialInfoDomainMapper;
	@Autowired
	MaterialTypeDomainMapper materialTypeDomainMapper;
	@Autowired
	MaterialTypeInfoDomainMapper materialTypeInfoDomainMapper;
	@Autowired
	ImageMagickUtil ImageMagickUtil;

	@Value("${web.upload-path}")
	private String resourceFTPUrl;

	@Value("${material.catch.png.url}")
	private String catchPngUrl;

	@Override
	public List<MaterialTypeDomain> getAllMaterialParentType() {
		return materialTypeDomainMapper.selectType();
	}

	@Override
	public List<MaterialChildTypeCoverInfosBo> getChildTypeInfoByParentCode(
			String parentCode, boolean isIcon) {
		List<MaterialChildTypeCoverInfosBo> resultList = new ArrayList<MaterialChildTypeCoverInfosBo>();
		List<MaterialTypeDomain> childTypeList = materialTypeDomainMapper
				.selectChildTypeByParentCode(parentCode);
		for (int i = 0; i < childTypeList.size(); i++) {
			MaterialChildTypeCoverInfosBo bo = new MaterialChildTypeCoverInfosBo();
			bo.setChildTypeDomain(childTypeList.get(i));
			bo.setMaterialOfChildTypeNum(materialTypeInfoDomainMapper
					.selectMaterialNumberByChildTypeCode(childTypeList.get(i)
							.getTypeCode()));
			bo.setCoverImgUrl(this.getChildTypeCoverImgUrl(childTypeList.get(i)
					.getTypeCode(), isIcon));
			resultList.add(bo);
		}
		return resultList;
	}

	public String getChildTypeCoverImgUrl(String childTypeCode, boolean isIcon) {
		/* 执行拼接图片方法，返回拼接出来的图片的缓存路径 */
		List<String> urlList = new ArrayList<String>();
		List<String> tempUrlList = null;
		String coverImgUrl = "";
		if (isIcon) {
			tempUrlList = materialTypeInfoDomainMapper
					.selectMaterialPngUrlListByIconChildCode(childTypeCode);
			for (String string : tempUrlList) {
				urlList.add((resourceFTPUrl + string).replaceAll("//", "/"));
			}
			try {
				String iconCoverUrl = ImageMagickUtil.iconDisplay(urlList);
				coverImgUrl = "/images/"
						+ iconCoverUrl.replace(
								catchPngUrl.replaceAll("//", "/"), "");
			} catch (IOException | InterruptedException | IM4JavaException e) {
				logger.info("封面图拼接失败");
				e.printStackTrace();
			}
		} else {
			String orignalUrl = resourceFTPUrl
					+ materialTypeInfoDomainMapper
							.selectMaterialThumbnailUrlByImgChildCode(childTypeCode);
			orignalUrl = orignalUrl.replaceAll("//", "/");
			String newImgUrl = catchPngUrl
					+ UUID.randomUUID().toString().replaceAll("-", "") + ".png";
			try {
				boolean copyResult = MaterialInfoServiceImpl.copy(orignalUrl,
						newImgUrl);
				if (copyResult) {
					String imgCoverUrl = newImgUrl;
					coverImgUrl = "/images/"
							+ imgCoverUrl.replace(
									catchPngUrl.replaceAll("//", "/"), "");
				}
			} catch (Exception exception) {
				logger.warn("有素材类别中没有素材存在！");
			}

		}
		return coverImgUrl;
	}

	@Override
	public PageQueryUtil getMaterialsByChildTypeCode(String childTypeCode,
			int currentPage, int pageSize) {
		int infoTotalNumber = materialTypeInfoDomainMapper
				.selectMaterialNumberByChildTypeCode(childTypeCode); // 总条数
		int pageNumber = infoTotalNumber % pageSize == 0 ? (infoTotalNumber / pageSize)
				: (infoTotalNumber / pageSize) + 1; // 总页数
		PageQueryUtil pageQueryUtil = new PageQueryUtil(pageSize, currentPage,
				infoTotalNumber);
		int start = (currentPage - 1) * pageSize;
		int end = (currentPage >= pageNumber) ? (infoTotalNumber - start)
				: pageSize;
		List<MaterialInfoDomain> resultList = materialInfoDomainMapper
				.selectMaterialInfoWithchildTypeCodeByPage(childTypeCode,
						start, end);
		pageQueryUtil.setObject(resultList);
		return pageQueryUtil;
	}

	@Override
	public List<MaterialTypeDomain> getMaterialTypeInfoByChildTypeCode(
			String childTypeCode) {
		return materialTypeDomainMapper
				.selectTypeInfosByChildTypeCode(childTypeCode);
	}

	@Override
	public Map<String, Object> getMaterialAndUserInfoById(Long materialId) {
		MaterialDetailsInfoBo resultBo = materialInfoDomainMapper
				.selectMaterialAndUserInfoById(materialId);
		resultBo.setUploadFormatTime(resultBo.getUploadTime());
		List<MaterialTypeInfoDomain> materialTypeList = materialTypeInfoDomainMapper
				.selectMaterialTypeInfosByMaterialInfoId(materialId);
		boolean isIcon = true;
		for (MaterialTypeInfoDomain materialTypeInfoDomain : materialTypeList) {
			if (!MaterialTypeConstants.MATERIAL_TYPE_ICON_PARENT_TYPE
					.equalsIgnoreCase(materialTypeInfoDomain
							.getMaterialTypeCodeParent().trim())) {
				isIcon = false;
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("MaterialDetailsInfoBo", resultBo);
		resultMap.put("isIcon", isIcon);
		return resultMap;
	}

	@Override
	public List<MaterialInfoDomain> getMaterialListByChildTypeCodeAndNumber(
			String childTypeCode, int start, int number) {
		int infoTotalNumber = materialTypeInfoDomainMapper
				.selectMaterialNumberByChildTypeCode(childTypeCode); // 总条数
		int end = (infoTotalNumber < start + number) ? (infoTotalNumber - start) : number;
		List<MaterialInfoDomain> resultList = null;
		if(end >= 0){
			resultList = materialInfoDomainMapper.
					selectMaterialInfoWithchildTypeCodeByPage(childTypeCode, start, end);
		}
		return resultList;
	}

	@Override
	public List<MaterialChildTypeCoverInfosBo> getChildTypeInfosByParentCodeWithoutCover(
			String parentCode, boolean isIcon) {
		List<MaterialChildTypeCoverInfosBo> resultList = new ArrayList<MaterialChildTypeCoverInfosBo>();
		List<MaterialTypeDomain> childTypeList = materialTypeDomainMapper
				.selectChildTypeByParentCode(parentCode);
		for (int i = 0; i < childTypeList.size(); i++) {
			MaterialChildTypeCoverInfosBo bo = new MaterialChildTypeCoverInfosBo();
			bo.setChildTypeDomain(childTypeList.get(i));
			bo.setMaterialOfChildTypeNum(materialTypeInfoDomainMapper
					.selectMaterialNumberByChildTypeCode(childTypeList.get(i)
							.getTypeCode()));
			resultList.add(bo);
		}
		return resultList;
	}
}
