package com.check.bean;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.check.util.DateStrUtil;

@Repository
@Scope("prototype")
public class Report implements java.io.Serializable {
	private static final long serialVersionUID = -227050691570499265L;

	private Integer id;
	private Integer sign;
	private String title;
	private String author;
	private String content;
	private String link;
	private Date create_date;
	private String remark;
	private String uid;
	private String ppid;
	private String cd;
	private Double need_price;
	private String wfid;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getCd() {
		return DateStrUtil.date2str(this.create_date);
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public Double getNeed_price() {
		return need_price;
	}
	public void setNeed_price(Double need_price) {
		this.need_price = need_price;
	}
	public String getWfid() {
		return wfid;
	}
	public void setWfid(String wfid) {
		this.wfid = wfid;
	}
}