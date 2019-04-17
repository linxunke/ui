package com.ztzh.ui.bo;

import java.util.Date;

public class MaterialESBo {
    private Long createUserId;

    private Long canvasInfoIdPrivate;

    private Long canvasInfoIdPublic;

    private String materialName;

    private String materialDescription;

    private String materialType;

    private String materialUrl;

    private String thumbnailUrl;

    private String pngUrl;

    private Date uploadTime;

    private Integer colorType;
    
	private String materialTypeCodeParent;

	private String materialTypeCodeChild;

	private String materialStyleCode;
	
	private Integer countDownload;
	
	private String sort;

	private Float colorPercentage;

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

	public Integer getCountDownload() {
		return countDownload;
	}

	public void setCountDownload(Integer countDownload) {
		this.countDownload = countDownload;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Float getColorPercentage() {
		return colorPercentage;
	}

	public void setColorPercentage(Float colorPercentage) {
		this.colorPercentage = colorPercentage;
	}

	public MaterialESBo(Long createUserId, Long canvasInfoIdPrivate, Long canvasInfoIdPublic, String materialName,
			String materialDescription, String materialType, String materialUrl, String thumbnailUrl, String pngUrl,
			Date uploadTime, Integer colorType, String materialTypeCodeParent, String materialTypeCodeChild,
			String materialStyleCode, Integer countDownload, String sort, Float colorPercentage) {
		super();
		this.createUserId = createUserId;
		this.canvasInfoIdPrivate = canvasInfoIdPrivate;
		this.canvasInfoIdPublic = canvasInfoIdPublic;
		this.materialName = materialName;
		this.materialDescription = materialDescription;
		this.materialType = materialType;
		this.materialUrl = materialUrl;
		this.thumbnailUrl = thumbnailUrl;
		this.pngUrl = pngUrl;
		this.uploadTime = uploadTime;
		this.colorType = colorType;
		this.materialTypeCodeParent = materialTypeCodeParent;
		this.materialTypeCodeChild = materialTypeCodeChild;
		this.materialStyleCode = materialStyleCode;
		this.countDownload = countDownload;
		this.sort = sort;
		this.colorPercentage = colorPercentage;
	}

	public MaterialESBo() {
		super();
	}

	@Override
	public String toString() {
		return "MaterialESBo [createUserId=" + createUserId + ", canvasInfoIdPrivate=" + canvasInfoIdPrivate
				+ ", canvasInfoIdPublic=" + canvasInfoIdPublic + ", materialName=" + materialName
				+ ", materialDescription=" + materialDescription + ", materialType=" + materialType + ", materialUrl="
				+ materialUrl + ", thumbnailUrl=" + thumbnailUrl + ", pngUrl=" + pngUrl + ", uploadTime=" + uploadTime
				+ ", colorType=" + colorType + ", materialTypeCodeParent=" + materialTypeCodeParent
				+ ", materialTypeCodeChild=" + materialTypeCodeChild + ", materialStyleCode=" + materialStyleCode
				+ ", countDownload=" + countDownload + ", sort=" + sort + ", colorPercentage=" + colorPercentage + "]";
	}
	

	
	

}
