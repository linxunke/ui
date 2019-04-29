package com.ztzh.ui.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztzh.ui.controller.UserController;

public class MaterialDetailsInfoBo {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private Long id;

	private Long createUserId;

	private String createUserHeadImgUrl;

	private String createUserName;

	private Long canvasInfoIdPrivate;

	private Long canvasInfoIdPublic;

	private String canvasName;

	private String materialName;

	private String materialDescription;

	private String materialType;

	private String materialUrl;

	private String thumbnailUrl;

	private String pngUrl; 
	
	private String uploadFormatTime;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date uploadTime;

	private Integer colorType;

	private Float colorPercentage;

	private Integer collectionCount;

	private Integer downloadCount;

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

	public String getCreateUserHeadImgUrl() {
		return createUserHeadImgUrl;
	}

	public void setCreateUserHeadImgUrl(String createUserHeadImgUrl) {
		this.createUserHeadImgUrl = createUserHeadImgUrl;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date formatedDate = null;
		try {
			formatedDate = sdf.parse(sdf.format(uploadTime));
			//formatedDate = sdf.parse(uploadTime.toString());
		} catch (ParseException e) {
			logger.warn("MaterialDetailsInfoBo中的日期格式转换失败");
		}
		logger.info(uploadTime.toString());
		this.uploadTime = formatedDate;
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

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	
	public String getUploadFormatTime() {
		return uploadFormatTime;
	}

	public void setUploadFormatTime(Date uploadTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date formatedDate = null;
		try {
			formatedDate = sdf.parse(sdf.format(uploadTime));
			//formatedDate = sdf.parse(uploadTime.toString());
		} catch (ParseException e) {
			logger.warn("MaterialDetailsInfoBo中的日期格式转换失败");
		}
		logger.info(uploadTime.toString());
		this.uploadFormatTime = sdf.format(uploadTime);
	}

	@Override
	public String toString() {
		return "MaterialDetailsInfoBo [logger=" + logger + ", id=" + id
				+ ", createUserId=" + createUserId + ", createUserHeadImgUrl="
				+ createUserHeadImgUrl + ", createUserName=" + createUserName
				+ ", canvasInfoIdPrivate=" + canvasInfoIdPrivate
				+ ", canvasInfoIdPublic=" + canvasInfoIdPublic
				+ ", canvasName=" + canvasName + ", materialName="
				+ materialName + ", materialDescription=" + materialDescription
				+ ", materialType=" + materialType + ", materialUrl="
				+ materialUrl + ", thumbnailUrl=" + thumbnailUrl + ", pngUrl="
				+ pngUrl + ", uploadFormatTime=" + uploadFormatTime
				+ ", uploadTime=" + uploadTime + ", colorType=" + colorType
				+ ", colorPercentage=" + colorPercentage + ", collectionCount="
				+ collectionCount + ", downloadCount=" + downloadCount + "]";
	}



}
