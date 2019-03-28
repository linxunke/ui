package com.ztzh.ui.service;

import com.ztzh.ui.bo.UploadMaterialsBo;

public interface UploadMaterialsService {
	public UploadMaterialsBo getMaterialTypes();
	
	public boolean putAllMaterialInfoInElasticsearch();
}
