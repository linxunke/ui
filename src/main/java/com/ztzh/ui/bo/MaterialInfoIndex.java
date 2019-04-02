package com.ztzh.ui.bo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "materialinfo", type="docs", shards=5, replicas=1)
public class MaterialInfoIndex {
	@Id
	private Long id;
	@Field(type=FieldType.Long)
    private Long createUserId;
	@Field(type=FieldType.Long)
    private Long canvasInfoIdPrivate;
	@Field(type=FieldType.Long)
    private Long canvasInfoIdPublic;
	@Field(type=FieldType.String, analyzer="ik")
    private String materialName;
	@Field(type=FieldType.String, analyzer="ik")
    private String materialDescription;
	@Field(type=FieldType.String)
    private String materialType;
	@Field(type=FieldType.String)
    private String materialUrl;
	@Field(type=FieldType.String)
    private String thumbnailUrl;
	@Field(type=FieldType.String)
    private String pngUrl;
	@Field(type=FieldType.Date)
    private Date uploadTime;
	@Field(type=FieldType.Integer)
    private Integer colorType;
	@Field(type=FieldType.Float)
    private Float colorPercentage;
	
	private List<MaterialTypeInfoIndex> materialTypeInfoIndex;
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
	public List<MaterialTypeInfoIndex> getMaterialTypeInfoIndex() {
		return materialTypeInfoIndex;
	}
	public void setMaterialTypeInfoIndex(List<MaterialTypeInfoIndex> materialTypeInfoIndex) {
		this.materialTypeInfoIndex = materialTypeInfoIndex;
	}
	public MaterialInfoIndex(Long id, Long createUserId, Long canvasInfoIdPrivate, Long canvasInfoIdPublic,
			String materialName, String materialDescription, String materialType, String materialUrl,
			String thumbnailUrl, String pngUrl, Date uploadTime, Integer colorType, Float colorPercentage,
			List<MaterialTypeInfoIndex> materialTypeInfoIndex) {
		super();
		this.id = id;
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
		this.colorPercentage = colorPercentage;
		this.materialTypeInfoIndex = materialTypeInfoIndex;
	}
	public MaterialInfoIndex() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MaterialInfoIndex [id=" + id + ", createUserId=" + createUserId + ", canvasInfoIdPrivate="
				+ canvasInfoIdPrivate + ", canvasInfoIdPublic=" + canvasInfoIdPublic + ", materialName=" + materialName
				+ ", materialDescription=" + materialDescription + ", materialType=" + materialType + ", materialUrl="
				+ materialUrl + ", thumbnailUrl=" + thumbnailUrl + ", pngUrl=" + pngUrl + ", uploadTime=" + uploadTime
				+ ", colorType=" + colorType + ", colorPercentage=" + colorPercentage + ", materialTypeInfoIndex="
				+ materialTypeInfoIndex + "]";
	}
	
	
	
}
