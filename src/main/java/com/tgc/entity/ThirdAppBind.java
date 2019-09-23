package com.tgc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "third_app_bind")
public class ThirdAppBind implements Serializable{
	/**
	 * 必须要实现序列化接口才能使用redis缓存
	 */
	private static final long serialVersionUID = 1542370946090407431L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private String openId;
	@Column
	private String appType;
	@Column
	private Integer userId;
	@Column
	private Integer status;
	private Date createTime;
	

	// ..get/set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
