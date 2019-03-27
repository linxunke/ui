package com.ztzh.ui.service;

import java.util.List;

import com.ztzh.ui.bo.UploadMaterialsBo;
import com.ztzh.ui.po.MaterialInfoDomain;
import com.ztzh.ui.po.MaterialTypeInfoDomain;

public interface UploadMaterialsService {
	public UploadMaterialsBo getMaterialTypes();
	public int addMaterialInfo(MaterialInfoDomain materialInfo);
	public int addMaterialTypeInfo(List<MaterialTypeInfoDomain> typesInfoList);
	public Long getMaterialIdByMaterialUrl(String materialUrl);
}
