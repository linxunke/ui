package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class CanvasInfoDomain implements Serializable{
	
	private static final long serialVersionUID = 7656017338131623779L;

	private Long id;

    private Long userId;

    private String canvasName;

    private String describeInfo;

    private Integer canvasType;

    private Date createTime;

    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCanvasName() {
        return canvasName;
    }

    public void setCanvasName(String canvasName) {
        this.canvasName = canvasName == null ? null : canvasName.trim();
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo == null ? null : describeInfo.trim();
    }

    public Integer getCanvasType() {
        return canvasType;
    }

    public void setCanvasType(Integer canvasType) {
        this.canvasType = canvasType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

	@Override
	public String toString() {
		return "CanvasInfoDomain [id=" + id + ", userId=" + userId
				+ ", canvasName=" + canvasName + ", describeInfo="
				+ describeInfo + ", canvasType=" + canvasType + ", createTime="
				+ createTime + ", isValid=" + isValid + "]";
	}
    
}