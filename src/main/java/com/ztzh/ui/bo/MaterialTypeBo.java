package com.ztzh.ui.bo;

import java.util.List;

import com.ztzh.ui.po.MaterialTypeDomain;

public class MaterialTypeBo {

	private String typeName;
	private String typeCode;
	private List<MaterialTypeDomain> childTypeName;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public List<MaterialTypeDomain> getChildTypeName() {
		return childTypeName;
	}
	public void setChildTypeName(List<MaterialTypeDomain> childTypeName) {
		this.childTypeName = childTypeName;
	}
	@Override
	public String toString() {
		return "MaterialTypeBo [typeName=" + typeName + ", typeCode="
				+ typeCode + ", childTypeName=" + childTypeName + "]";
	}

	
}
