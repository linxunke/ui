package com.ztzh.ui.bo;

public class MaterialCountByParentType {
	private String materialTypeCodeParent;
	private Long materialCount;
	public String getMaterialTypeCodeParent() {
		return materialTypeCodeParent;
	}
	public void setMaterialTypeCodeParent(String materialTypeCodeParent) {
		this.materialTypeCodeParent = materialTypeCodeParent;
	}
	public Long getMaterialCount() {
		return materialCount;
	}
	public void setMaterialCount(Long materialCount) {
		this.materialCount = materialCount;
	}
	
	public MaterialCountByParentType() {
		super();
	}
	@Override
	public String toString() {
		return "MaterialCountByParentType [materialTypeCodeParent=" + materialTypeCodeParent + ", materialCount="
				+ materialCount + "]";
	}
	

}
