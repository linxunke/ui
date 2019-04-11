package com.ztzh.ui.bo;

import java.util.Date;
import java.util.List;

import com.ztzh.ui.po.MaterialTypeInfoDomain;

public class MaterialAndTypeInfoBo {
	private Long id;

    private Long createUserId;

    private Long canvasInfoIdPrivate;

    private Long canvasInfoIdPublic;
    
    private String canvasName;
    
    private String canvasDescribeInfo;

    private String materialName;

    private String materialDescription;

    private String materialType;

    private String materialUrl;

    private String thumbnailUrl;

    private String pngUrl;

    private Date uploadTime;

    private Integer colorType;

    private Float colorPercentage;

    private Integer isValid;
    
    private List<MaterialTypeInfoDomain> materialTypeInfoList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getCanvasInfoIdPrivate() {
		return canvasInfoIdPrivate;
	}

	public void setCanvasInfoIdPrivate(Long canvasInfoIdPrivate) {
		this.canvasInfoIdPrivate = canvasInfoIdPrivate;
	}

	public Long getCanvasInfoIdPublic() {
		return canvasInfoIdPublic;
	}

	public void setCanvasInfoIdPublic(Long canvasInfoIdPublic) {
		this.canvasInfoIdPublic = canvasInfoIdPublic;
	}

	public String getCanvasName() {
		return canvasName;
	}

	public void setCanvasName(String canvasName) {
		this.canvasName = canvasName;
	}

	public String getCanvasDescribeInfo() {
		return canvasDescribeInfo;
	}

	public void setCanvasDescribeInfo(String canvasDescribeInfo) {
		this.canvasDescribeInfo = canvasDescribeInfo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialUrl() {
		return materialUrl;
	}

	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getPngUrl() {
		return pngUrl;
	}

	public void setPngUrl(String pngUrl) {
		this.pngUrl = pngUrl;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public Float getColorPercentage() {
		return colorPercentage;
	}

	public void setColorPercentage(Float colorPercentage) {
		this.colorPercentage = colorPercentage;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public List<MaterialTypeInfoDomain> getMaterialTypeInfoList() {
		return materialTypeInfoList;
	}

	public void setMaterialTypeInfoList(
			List<MaterialTypeInfoDomain> materialTypeInfoList) {
		this.materialTypeInfoList = materialTypeInfoList;
	}

	@Override
	public String toString() {
		return "MaterialAndTypeInfoBo [id=" + id + ", createUserId="
				+ createUserId + ", canvasInfoIdPrivate=" + canvasInfoIdPrivate
				+ ", canvasInfoIdPublic=" + canvasInfoIdPublic
				+ ", canvasName=" + canvasName + ", canvasDescribeInfo="
				+ canvasDescribeInfo + ", materialName=" + materialName
				+ ", materialDescription=" + materialDescription
				+ ", materialType=" + materialType + ", materialUrl="
				+ materialUrl + ", thumbnailUrl=" + thumbnailUrl + ", pngUrl="
				+ pngUrl + ", uploadTime=" + uploadTime + ", colorType="
				+ colorType + ", colorPercentage=" + colorPercentage
				+ ", isValid=" + isValid + ", materialTypeInfoList="
				+ materialTypeInfoList + "]";
	}
    
}
