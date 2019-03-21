package com.ztzh.ui.dao;

import java.util.List;

import com.ztzh.ui.po.CanvasInfoDomain;

public interface CanvasInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CanvasInfoDomain record);

    int insertSelective(CanvasInfoDomain record);

    CanvasInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CanvasInfoDomain record);

    int updateByPrimaryKey(CanvasInfoDomain record);
    
    List<CanvasInfoDomain> selectByUserId(Long userId);
}