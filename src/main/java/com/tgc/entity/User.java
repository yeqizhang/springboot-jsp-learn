package com.tgc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "users")
public class User implements Serializable{
	/**
	 * 必须要实现序列化接口才能使用redis缓存
	 */
	private static final long serialVersionUID = 1542370946090407431L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private String name;
	@Column
	private Integer age;

	// ..get/set方法
	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Integer getAge() {

		return age;
	}

	public void setAge(Integer age) {

		this.age = age;
	}

}
