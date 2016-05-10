package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="questionnaire")
public class Questionnaire extends BaseEntity {
	private String uid;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	private String name;
	private String mobile;
	private Integer sex;
	private Long birthday;
	private Integer age;
	private Long validatetime;
	private String validatecode;
	private String place;
	private Integer occupation;
	private Integer income;
	private String city;
	private String address;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	private Integer stockyear;
	private Integer question1;
	private Integer question2;
	private String  question3;
	private Integer question4;
	private Integer question5;
	private Integer question6;
	private Integer question7;
	public String getValidatecode() {
		return validatecode;
	}
	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}
	public Long getValidatetime() {
		return validatetime;
	}
	public void setValidatetime(Long validatetime) {
		this.validatetime = validatetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public Integer getIncome() {
		return income;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public Integer getStockyear() {
		return stockyear;
	}
	public void setStockyear(Integer stockyear) {
		this.stockyear = stockyear;
	}
	public Integer getQuestion1() {
		return question1;
	}
	public void setQuestion1(Integer question1) {
		this.question1 = question1;
	}
	public Integer getQuestion2() {
		return question2;
	}
	public void setQuestion2(Integer question2) {
		this.question2 = question2;
	}
	public String getQuestion3() {
		return question3;
	}
	public void setQuestion3(String question3) {
		this.question3 = question3;
	}
	public Integer getQuestion4() {
		return question4;
	}
	public void setQuestion4(Integer question4) {
		this.question4 = question4;
	}
	public Integer getQuestion5() {
		return question5;
	}
	public void setQuestion5(Integer question5) {
		this.question5 = question5;
	}
	public Integer getQuestion6() {
		return question6;
	}
	public void setQuestion6(Integer question6) {
		this.question6 = question6;
	}
	public Integer getQuestion7() {
		return question7;
	}
	public void setQuestion7(Integer question7) {
		this.question7 = question7;
	}
}