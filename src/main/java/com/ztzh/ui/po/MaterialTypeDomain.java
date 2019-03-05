package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class MaterialTypeDomain implements Serializable{
	
	private static final long serialVersionUID = 3876606059160583527L;

	private Long id;

    private String typeName;

    private String typeCode;

    private Integer grade;

    private String parentCode;

    private Date updateTime;

    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

	@Override
	public String toString() {
		return "MaterialTypeDomain [id=" + id + ", typeName=" + typeName
				+ ", typeCode=" + typeCode + ", grade=" + grade
				+ ", parentCode=" + parentCode + ", updateTime=" + updateTime
				+ ", isValid=" + isValid + "]";
	}
    
}