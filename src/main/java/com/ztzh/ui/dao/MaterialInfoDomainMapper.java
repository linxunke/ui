package com.ztzh.ui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ztzh.ui.bo.IconMaterialBo;
import com.ztzh.ui.bo.MaterialAndTypeInfoBo;
import com.ztzh.ui.bo.MaterialDetailsInfoBo;
import com.ztzh.ui.bo.MaterialInfoIndex;
import com.ztzh.ui.bo.MaterialTypeBo;
import com.ztzh.ui.bo.ThreeRecentUrlResultBo;
import com.ztzh.ui.po.CanvasInfoDomain;
import com.ztzh.ui.po.MaterialInfoDomain;

public interface MaterialInfoDomainMapper {
	int deleteByPrimaryKey(Long id);

	int insert(MaterialInfoDomain record);

	int insertSelective(MaterialInfoDomain record);

	MaterialInfoDomain selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MaterialInfoDomain record);

	int updateByPrimaryKey(MaterialInfoDomain record);

	List<MaterialInfoDomain> selectByCanvasId(Long canvasId);

	int deleteByCanvasId(@Param("canvasId") Long canvasId,
			@Param("userId") Long userId);

	int updateByCanvasInfoIdPrivate(
			@Param("materialInfoDomain") MaterialInfoDomain record,
			@Param("canvasId") Long canvasId, @Param("userId") Long userId);

	int addMaterialInfo(MaterialInfoDomain materialInfo);

	Long selectMaterialIdByMaterialUrl(String materialUrl);

	List<MaterialInfoIndex> getValidMaterialInfoForIndex();

	// 首页三张图
	List<ThreeRecentUrlResultBo> getThreeRecentMaterial();

	String selectTypeNameByChildCode(String childCode);

	List<IconMaterialBo> getIconMaterialBoByThumbnailUrls(
			@Param("thumbnailUrls") List<String> thumbnailUrls);

	List<String> getIconUrlsByCanvasId(@Param("canvasId") String canvasId);

	List<MaterialInfoDomain> queryByIds(@Param("idList") List<Long> idList);

	int deleteByIds(@Param("idList") List<Long> idList);

	List<MaterialInfoIndex> getValidMaterialInfoForIndexByIds(
			@Param("materialInfoIds") List<Long> materialInfoIds);

	int selectIconCount();

	int selectDrawingCount();

	List<MaterialTypeBo> selectTypeNameForBox();

	int getMaterialNumOfCanvasByCanvasId(Long canvasId);

	List<MaterialInfoDomain> selectMaterialInfoWithCanvasIdByPage(
			@Param("canvasId") Long canvasId, @Param("start") int start,
			@Param("end") int end);

	MaterialAndTypeInfoBo selectMaterialDetailsInfo(Long materialId);

	List<MaterialInfoDomain> selectMaterialInfoWithchildTypeCodeByPage(
			@Param("childTypeCode") String childTypeCode,
			@Param("start") int start, @Param("end") int end);
	
	MaterialDetailsInfoBo selectMaterialAndUserInfoById(Long materialId);
}