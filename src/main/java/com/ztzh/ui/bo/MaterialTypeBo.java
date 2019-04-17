package com.ztzh.ui.bo;

import java.util.List;

public class MaterialTypeBo {

	private String typeName;
	private String typeCode;
	private List<String> childTypeName;

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

	public List<String> getChildTypeName() {
		return childTypeName;
	}

	public void setChildTypeName(List<String> childTypeName) {
		this.childTypeName = childTypeName;
	}

	@Override
	public String toString() {
		return "MaterialTypeBo [typeName=" + typeName + ", typeCode="
				+ typeCode + ", childTypeName=" + childTypeName + "]";
	}

	

}
