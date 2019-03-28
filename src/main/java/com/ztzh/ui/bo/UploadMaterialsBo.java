package com.ztzh.ui.bo;

import java.util.List;

import com.ztzh.ui.po.MaterialTypeDomain;

public class UploadMaterialsBo {
	public List<MaterialTypeDomain> materialTypes; // 素材类型
	public List<MaterialTypeDomain> materialSegmentations; // 素材细分
	public List<MaterialTypeDomain> materialStyles; // 素材风格

	public List<MaterialTypeDomain> getMaterialTypes() {
		return materialTypes;
	}

	public void setMaterialTypes(List<MaterialTypeDomain> materialTypes) {
		this.materialTypes = materialTypes;
	}

	public List<MaterialTypeDomain> getMaterialSegmentations() {
		return materialSegmentations;
	}

	public void setMaterialSegmentations(
			List<MaterialTypeDomain> materialSegmentations) {
		this.materialSegmentations = materialSegmentations;
	}

	public List<MaterialTypeDomain> getMaterialStyles() {
		return materialStyles;
	}

	public void setMaterialStyles(List<MaterialTypeDomain> materialStyles) {
		this.materialStyles = materialStyles;
	}

	@Override
	public String toString() {
		return "UploadMaterialsBo [materialTypes=" + materialTypes
				+ ", materialSegmentations=" + materialSegmentations
				+ ", materialStyles=" + materialStyles + "]";
	}

}
