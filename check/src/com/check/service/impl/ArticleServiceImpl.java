package com.check.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.bean.Article;
import com.check.dao.ArticleDao;
import com.check.dao.BaseMybatisDao;
import com.check.service.ArticleService;

@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseMybatisServiceImpl<Article, String> implements ArticleService{
	@Resource(name = "articleDaoImpl")
	public void setBaseDao(BaseMybatisDao<Article, String> articleDao) {
		super.setBaseMybatisDao(articleDao);
	}
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;

}
