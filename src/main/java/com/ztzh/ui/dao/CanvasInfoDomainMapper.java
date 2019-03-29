package com.ztzh.ui.dao;


import java.util.ArrayList;

import com.ztzh.ui.bo.ManagementCanvasBo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

//>>>>>>> c8d3bc38bba75996d9de5971d890761e0e4c36f0
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
    
    List<ManagementCanvasBo> selectByUserId(Long userId);
    
    CanvasInfoDomain selectByCanvasName(@Param("canvasName")String canvasName,@Param("userId")Long userId);
}