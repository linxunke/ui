package com.ztzh.ui.dao;

import java.util.ArrayList;

import com.ztzh.ui.bo.ManagementCanvasBo;
import com.ztzh.ui.po.CanvasInfoDomain;

public interface CanvasInfoDomainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CanvasInfoDomain record);

    int insertSelective(CanvasInfoDomain record);

    CanvasInfoDomain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CanvasInfoDomain record);

    int updateByPrimaryKey(CanvasInfoDomain record);
    
    int selectCountByUserId(Long userId);
    
    ArrayList<ManagementCanvasBo> selectCanvasInfoByUserId(Long userId);
    
    
}