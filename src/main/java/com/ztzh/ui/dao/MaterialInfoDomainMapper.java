package com.ztzh.ui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ztzh.ui.bo.IconMaterialBo;
import com.ztzh.ui.bo.MaterialInfoIndex;
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
    
    int deleteByCanvasId(@Param("canvasId") Long canvasId,@Param("userId") Long userId);
    
    int updateByCanvasInfoIdPrivate (@Param("materialInfoDomain") MaterialInfoDomain record,@Param("canvasId") Long canvasId,@Param("userId") Long userId);
    
    int addMaterialInfo(MaterialInfoDomain materialInfo);
    
    Long selectMaterialIdByMaterialUrl(String materialUrl);

    List<MaterialInfoIndex> getValidMaterialInfoForIndex();
    
    List<IconMaterialBo> getIconMaterialBoByThumbnailUrls(@Param("thumbnailUrls") List<String> thumbnailUrls);

	List<String> getIconUrlsByCanvasId(@Param("canvasId") String canvasId);
	
	List<MaterialInfoDomain> queryByIds(@Param("idList") List<Long> idList);
	
	int deleteByIds(@Param("idList") List<Long> idList);
    
	int getMaterialNumOfCanvasByCanvasId(Long canvasId);
	
	List<MaterialInfoDomain> selectMaterialInfoWithCanvasIdByPage(@Param("canvasId")Long canvasId, @Param("start")int start, @Param("end")int end);
}