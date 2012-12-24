package com.check.bean;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.check.util.DateStrUtil;

@Repository
@Scope("prototype")
public class User implements java.io.Serializable {
	private static final long serialVersionUID = -227050691570498265L;

	private Integer id;
	private String username;
	private String password;
	private Integer sign;
	private Date create_date;
	private Date login_date;
	private String remark;
	private String cd;
	private String ld;
	private Integer flag;
	private Double account;
	private Double price;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getCd() {
		return DateStrUtil.date2str(this.create_date);
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getLd() {
		return DateStrUtil.date2str(this.login_date);
	}

	public void setLd(String ld) {
		this.ld = ld;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLogin_date() {
		return login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}