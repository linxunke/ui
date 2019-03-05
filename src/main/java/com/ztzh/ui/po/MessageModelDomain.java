package com.ztzh.ui.po;

import java.io.Serializable;

public class MessageModelDomain implements Serializable{

	private static final long serialVersionUID = 7098696077549450725L;

	private Long id;

    private String messageDetail;

    private Integer messageType;

    private Integer isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail == null ? null : messageDetail.trim();
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

	@Override
	public String toString() {
		return "MessageModelDomain [id=" + id + ", messageDetail="
				+ messageDetail + ", messageType=" + messageType + ", isValid="
				+ isValid + "]";
	}
}