package com.check.util;

import java.util.List;

public class PageListData {
	// 分页结果集
	private List dataList = null;
	// 记录总数
	private int totalcount = 0;
	// 每页显示记录数
	private int pageSize = 10;
	// 当前页数
	private int currentPage = 1;
	// 总页数
	private int totalPageCount = 1;
	//分页页脚
	private String footer;
	/*初始化分页组件*/
	
	public PageListData(){
		
	}
	
	public PageListData(int totalcount, int pageSize, int currentPage,
			List dataList) {
		setTotalcount(totalcount);
		setPageSize(pageSize);
		setCurrentPage(currentPage);
		setDataList(dataList);
		setFooter(getFooter());
	}
	/**
	 * 封装分页栏函数 必需被包含在某个Form之中
	 * 
	 * @return String pages 当前页号 pageSize 每页显示行数
	 */
	public String getFooter() {
		StringBuffer pageStr = new StringBuffer("");
		pageStr.append("<center><p class='pages'>");
		int totalPages = getTotalPageCount(); // 总页数
		int prevPage = currentPage - 1; // 上一页
		int nextPage = currentPage + 1; // 下一页
		pageStr.append("<span style='color:#6795B4;'>共有" + totalcount
				+ "条记录</span>&nbsp;&nbsp;");
		pageStr.append("<span style='color:#6795B4;'>第" + currentPage + "页/共"
				+ totalPages + "页</span>&nbsp;&nbsp;");
		if (currentPage > 1)
			pageStr
					.append("<span><a style='cursor: pointer;text-decoration:underline;color:#6795B4'onclick='document.getElementById(\"pages\").value=1;document.getElementById(\"pages\").form.submit();'>首页</a></span>&nbsp;&nbsp;");
		if (currentPage == 1)
			pageStr
					.append("<span style='color:#6795B4'>首页</span>&nbsp;&nbsp;&nbsp;&nbsp;");
		if (currentPage > 1)
			pageStr
					.append("<span><a style='cursor: pointer;text-decoration:underline;color:#6795B4' onclick='document.getElementById(\"pages\").value="
							+ prevPage
							+ ";document.getElementById(\"pages\").form.submit();'>上一页</a></span>&nbsp;&nbsp;");
		if (currentPage <= 1)
			pageStr
					.append("<span style='color:#6795B4'>上一页</span>&nbsp;&nbsp;");
		if (currentPage < totalPages)
			pageStr
					.append("<span><a style='cursor: pointer;text-decoration:underline;color:#6795B4;' onclick='document.getElementById(\"pages\").value="
							+ nextPage
							+ ";document.getElementById(\"pages\").form.submit();'>下一页</a></span>&nbsp;&nbsp;");
		if (currentPage >= totalPages)
			pageStr
					.append("<span style='color:#6795B4;'>下一页</span>&nbsp;&nbsp;");
		if (currentPage < totalPages)
			pageStr
					.append("<span><a style='cursor: pointer;text-decoration:underline;color:#6795B4;' onclick='document.getElementById(\"pages\").value="
							+ totalPages
							+ ";document.getElementById(\"pages\").form.submit();'>末页</a></span>&nbsp;&nbsp;");
		if (currentPage == totalPages)
			pageStr
					.append("<span style='color:#6795B4;'>末页</span>&nbsp;&nbsp;");
		pageStr
				.append("<span style='color:#6795B4;'>跳转至第:<input type='text' value='"
						+ currentPage
						+ "'id='jumpPageBox' size='2' onblur='checkCurrentPage(document.getElementById(\"jumpPageBox\").value,"
						+ totalPages
						+ ")'/>页<input class='right-button02' type='button' value='跳转' onclick='document.getElementById(\"pages\").value=document.getElementById(\"jumpPageBox\").value;document.getElementById(\"pages\").form.submit();'/></span>");
		pageStr.append("</p></center>");
		pageStr.append("<input type='hidden' value='" + currentPage
				+ "' name='currentPage' id='pages' />");
		pageStr.append("<input type='hidden' value='" + pageSize
				+ "' name='pageSize' id='pageSize' />");
		return pageStr.toString();
	}
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	//计算总页数，如果为0则置为1
	public int getTotalPageCount() {
		int p;
		if (totalcount % pageSize == 0) {
			p=totalcount / pageSize;
		} else {
			p=totalcount / pageSize + 1;
		}
		return p==0?1:p;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
}
