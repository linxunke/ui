package com.ztzh.ui.bo;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class MaterialTypeInfoIndex {
	@Field(type=FieldType.String)
	private String materialTypeCodeParent;
	@Field(type=FieldType.String)
	private String materialTypeCodeChild;
	@Field(type=FieldType.String)
	private String materialStyleCode;
	public String getMaterialTypeCodeParent() {
		return materialTypeCodeParent;
	}
	public void setMaterialTypeCodeParent(String materialTypeCodeParent) {
		this.materialTypeCodeParent = materialTypeCodeParent;
	}
	public String getMaterialTypeCodeChild() {
		return materialTypeCodeChild;
	}
	public void setMaterialTypeCodeChild(String materialTypeCodeChild) {
		this.materialTypeCodeChild = materialTypeCodeChild;
	}
	public String getMaterialStyleCode() {
		return materialStyleCode;
	}
	public void setMaterialStyleCode(String materialStyleCode) {
		this.materialStyleCode = materialStyleCode;
	}
	public MaterialTypeInfoIndex(String materialTypeCodeParent, String materialTypeCodeChild,
			String materialStyleCode) {
		super();
		this.materialTypeCodeParent = materialTypeCodeParent;
		this.materialTypeCodeChild = materialTypeCodeChild;
		this.materialStyleCode = materialStyleCode;
	}
	public MaterialTypeInfoIndex() {
		super();
	}
	@Override
	public String toString() {
		return "MaterialTypeInfoIndex [materialTypeCodeParent=" + materialTypeCodeParent + ", materialTypeCodeChild="
				+ materialTypeCodeChild + ", materialStyleCode=" + materialStyleCode + "]";
	}
	

}
