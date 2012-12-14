package com.check.bean;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.check.util.DateStrUtil;

@Repository
@Scope("prototype")
public class Article implements java.io.Serializable {
	private static final long serialVersionUID = -227050691570498265L;

	private Integer id;
	private String title;
	private String author;
	private String content;
	private Date create_date;
	private String remark;
	private Integer type;
	private String cd;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getCd() {
		return DateStrUtil.date2str(this.create_date);
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	
}