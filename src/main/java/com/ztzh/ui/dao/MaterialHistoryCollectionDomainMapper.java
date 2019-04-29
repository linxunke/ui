package com.ztzh.ui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ztzh.ui.po.MaterialHistoryCollectionDomain;
import com.ztzh.ui.po.MaterialInfoDomain;

public interface MaterialHistoryCollectionDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialHistoryCollectionDomain record);

    int insertSelective(MaterialHistoryCollectionDomain record);

    MaterialHistoryCollectionDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialHistoryCollectionDomain record);

    int updateByPrimaryKey(MaterialHistoryCollectionDomain record);
    
    int deleteByMaterialInfoIds(@Param("materialInfoIds")List<Long> materialInfoIds);
    
    boolean deleteAllMaterial(@Param("userId")Long userId, @Param("type")int type);
    
    int countByMaterialInfoId(Long materialInfoId);
    
    int cancelCollection(@Param("materialInfoId") Long materialInfoId,@Param("userInfoId") Long userInfoId);
    
    List<Long> SelectByUserInfoId(Long userInfoId);
    
    List<MaterialInfoDomain> SelectByUserIdForHistory(@Param("userId") Long userId,@Param("type") int type,@Param("start")int start,@Param("PageSize")int PageSize);
    
    int countByUserId(@Param("userInfoId") Long userInfoId,@Param("type")int type);
}