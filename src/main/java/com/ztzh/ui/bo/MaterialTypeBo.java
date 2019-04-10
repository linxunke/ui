package com.ztzh.ui.bo;

public class MaterialTypeBo {

	private String typeName;
	private String typeCode;

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

	@Override
	public String toString() {
		return "MaterialTypeBo [typeName=" + typeName + ", typeCode="
				+ typeCode + "]";
	}

}
