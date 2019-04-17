package com.ztzh.ui.bo;

import java.util.List;

import com.ztzh.ui.po.MaterialTypeDomain;

public class MaterialChildTypeCoverInfosBo {
	private String coverImgUrl;
	private MaterialTypeDomain childTypeDomain;
	private int materialOfChildTypeNum;

	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	public MaterialTypeDomain getChildTypeDomain() {
		return childTypeDomain;
	}

	public void setChildTypeDomain(MaterialTypeDomain childTypeDomain) {
		this.childTypeDomain = childTypeDomain;
	}

	public int getMaterialOfChildTypeNum() {
		return materialOfChildTypeNum;
	}

	public void setMaterialOfChildTypeNum(int materialOfChildTypeNum) {
		this.materialOfChildTypeNum = materialOfChildTypeNum;
	}

	@Override
	public String toString() {
		return "MaterialChildTypeCoverInfosBo [coverImgUrl=" + coverImgUrl
				+ ", childTypeDomain=" + childTypeDomain
				+ ", materialOfChildTypeNum=" + materialOfChildTypeNum + "]";
	}

}
