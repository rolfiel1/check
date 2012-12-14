package com.check.dao.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.check.bean.Article;
import com.check.dao.ArticleDao;

@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseMybatisDaoImpl<Article, String> implements
		ArticleDao {
	private static Logger logger = Logger.getLogger(ArticleDaoImpl.class);

}
