package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_user_info")
public class UserInfo  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String tname;
	private boolean sex;
	private String idcard;
	private Integer birthdate;
	private String education;
	private Integer graduatedYear;
	private String graduatedSchool;
	private String marriage;
	private short haveChild;
	private short haveHouse;
	private short houseLoan;
	private short haveCar;
	private short carLoan;
	private Integer idcardProvince;
	private Integer idcardCity;
	private String province;
	private String city;
	private String address;
	private String addressPhone;
	private String avatar;
	private short salary;
	private short work;
    private String industry;
    private String position;
    private String income;
	private short isadress;
	private short edu;
	private short car;
	private short house;
	private short credit;
	private short oktime;
	private short marry;
	private short other;
	private short examine;
	
	@OneToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)               
	@JoinColumn(name="uid", nullable=false)  
	private WUser wuser;

	@Column(name="tname")
	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Column(name="sex")
	public boolean getSex() {
		return this.sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@Column(name="idcard")
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name="birthdate")
	public Integer getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Integer birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name="education")
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name="graduated_year")
	public Integer getGraduatedYear() {
		return this.graduatedYear;
	}

	public void setGraduatedYear(Integer graduatedYear) {
		this.graduatedYear = graduatedYear;
	}

	@Column(name="graduated_school")
	public String getGraduatedSchool() {
		return this.graduatedSchool;
	}

	public void setGraduatedSchool(String graduatedSchool) {
		this.graduatedSchool = graduatedSchool;
	}

	@Column(name="marriage")
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name="have_child")
	public short getHaveChild() {
		return this.haveChild;
	}

	public void setHaveChild(short haveChild) {
		this.haveChild = haveChild;
	}

	@Column(name="have_house")
	public short getHaveHouse() {
		return this.haveHouse;
	}

	public void setHaveHouse(short haveHouse) {
		this.haveHouse = haveHouse;
	}

	@Column(name="house_loan")
	public short getHouseLoan() {
		return this.houseLoan;
	}

	public void setHouseLoan(short houseLoan) {
		this.houseLoan = houseLoan;
	}

	@Column(name="have_car")
	public short getHaveCar() {
		return this.haveCar;
	}

	public void setHaveCar(short haveCar) {
		this.haveCar = haveCar;
	}

	@Column(name="car_loan")
	public short getCarLoan() {
		return this.carLoan;
	}

	public void setCarLoan(short carLoan) {
		this.carLoan = carLoan;
	}

	@Column(name="idcard_province")
	public Integer getIdcardProvince() {
		return this.idcardProvince;
	}

	public void setIdcardProvince(Integer idcardProvince) {
		this.idcardProvince = idcardProvince;
	}

	@Column(name="idcard_city")
	public Integer getIdcardCity() {
		return this.idcardCity;
	}

	public void setIdcardCity(Integer idcardCity) {
		this.idcardCity = idcardCity;
	}

	@Column(name="province")
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name="city")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="address", nullable=false)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="address_phone", nullable=false)
	public String getAddressPhone() {
		return this.addressPhone;
	}

	public void setAddressPhone(String addressPhone) {
		this.addressPhone = addressPhone;
	}

	@Column(name="avatar")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name="salary", nullable=false)
	public short getSalary() {
		return this.salary;
	}

	public void setSalary(short salary) {
		this.salary = salary;
	}

	@Column(name="work", nullable=false)
	public short getWork() {
		return this.work;
	}

	public void setWork(short work) {
		this.work = work;
	}

	@Column(name="industry")
    public String getIndustry() {
        return this.industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    @Column(name="position")
    public String getPosition() {
        return this.position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    @Column(name="income")
    public String getIncome() {
        return this.income;
    }
    
    public void setIncome(String income) {
        this.income = income;
    }
	
	@Column(name="isadress")
	public short getIsadress() {
		return this.isadress;
	}

	public void setIsadress(short isadress) {
		this.isadress = isadress;
	}

	@Column(name="edu")
	public short getEdu() {
		return this.edu;
	}

	public void setEdu(short edu) {
		this.edu = edu;
	}

	@Column(name="car")
	public short getCar() {
		return this.car;
	}

	public void setCar(short car) {
		this.car = car;
	}

	@Column(name="house")
	public short getHouse() {
		return this.house;
	}

	public void setHouse(short house) {
		this.house = house;
	}

	@Column(name="credit")
	public short getCredit() {
		return this.credit;
	}

	public void setCredit(short credit) {
		this.credit = credit;
	}

	@Column(name="oktime")
	public short getOktime() {
		return this.oktime;
	}

	public void setOktime(short oktime) {
		this.oktime = oktime;
	}

	@Column(name="marry")
	public short getMarry() {
		return this.marry;
	}

	public void setMarry(short marry) {
		this.marry = marry;
	}

	@Column(name="other")
	public short getOther() {
		return this.other;
	}

	public void setOther(short other) {
		this.other = other;
	}

	@Column(name="examine")
	public short getExamine() {
		return this.examine;
	}

	public void setExamine(short examine) {
		this.examine = examine;
	}


	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}
}