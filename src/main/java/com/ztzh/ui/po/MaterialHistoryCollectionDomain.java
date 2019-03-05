package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class MaterialHistoryCollectionDomain implements Serializable{

	private static final long serialVersionUID = -3043407906353628506L;

	private Long id;

    private Long materialInfoId;

    private Long userInfoId;

    private Integer type;

    private Date operateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialInfoId() {
        return materialInfoId;
    }

    public void setMaterialInfoId(Long materialInfoId) {
        this.materialInfoId = materialInfoId;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

	@Override
	public String toString() {
		return "MaterialHistoryCollectionDomain [id=" + id
				+ ", materialInfoId=" + materialInfoId + ", userInfoId="
				+ userInfoId + ", type=" + type + ", operateTime="
				+ operateTime + "]";
	}
    
}