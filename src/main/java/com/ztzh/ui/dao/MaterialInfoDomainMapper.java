package com.ztzh.ui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
}