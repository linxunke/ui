package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class MaterialTypeInfoDomain implements Serializable{
	
	private static final long serialVersionUID = 4004421644295185564L;

	private Long id;

    private Long materailInfoId;

    private String materialTypeCodeParent;

    private String materialTypeCodeChild;

    private String materialStyleCode;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterailInfoId() {
        return materailInfoId;
    }

    public void setMaterailInfoId(Long materailInfoId) {
        this.materailInfoId = materailInfoId;
    }

    public String getMaterialTypeCodeParent() {
        return materialTypeCodeParent;
    }

    public void setMaterialTypeCodeParent(String materialTypeCodeParent) {
        this.materialTypeCodeParent = materialTypeCodeParent == null ? null : materialTypeCodeParent.trim();
    }

    public String getMaterialTypeCodeChild() {
        return materialTypeCodeChild;
    }

    public void setMaterialTypeCodeChild(String materialTypeCodeChild) {
        this.materialTypeCodeChild = materialTypeCodeChild == null ? null : materialTypeCodeChild.trim();
    }

    public String getMaterialStyleCode() {
        return materialStyleCode;
    }

    public void setMaterialStyleCode(String materialStyleCode) {
        this.materialStyleCode = materialStyleCode == null ? null : materialStyleCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "MaterialTypeInfoDomain [id=" + id + ", materailInfoId="
				+ materailInfoId + ", materialTypeCodeParent="
				+ materialTypeCodeParent + ", materialTypeCodeChild="
				+ materialTypeCodeChild + ", materialStyleCode="
				+ materialStyleCode + ", createTime=" + createTime + "]";
	}
    
}