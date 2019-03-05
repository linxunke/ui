package com.ztzh.ui.po;

import java.io.Serializable;
import java.util.Date;

public class MessageInfoDomain implements Serializable{
	
	private static final long serialVersionUID = 2426748002516794263L;

	private Long id;

    private Long userId;

    private String message;

    private Date acceptTime;

    private Date readedTime;

    private Integer isRead;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getReadedTime() {
        return readedTime;
    }

    public void setReadedTime(Date readedTime) {
        this.readedTime = readedTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

	@Override
	public String toString() {
		return "MessageInfoDomain [id=" + id + ", userId=" + userId
				+ ", message=" + message + ", acceptTime=" + acceptTime
				+ ", readedTime=" + readedTime + ", isRead=" + isRead
				+ ", isValid=" + isValid + "]";
	}
    
}