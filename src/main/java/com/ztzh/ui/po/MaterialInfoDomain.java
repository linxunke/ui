package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class MaterialInfoDomain implements Serializable{

	private static final long serialVersionUID = -8366586613293380956L;

	private Long id;

    private Long createUserId;

    private Long canvasInfoIdPrivate;

    private Long canvasInfoIdPublic;

    private String materialName;

    private String materialDescription;

    private String materialType;

    private String materialUrl;

    private String thumbnailUrl;

    private Date uploadTime;

    private Integer colorType;

    private Float colorPercentage;

    private Integer isValid;

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription == null ? null : materialDescription.trim();
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType == null ? null : materialType.trim();
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl == null ? null : materialUrl.trim();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl == null ? null : thumbnailUrl.trim();
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
}