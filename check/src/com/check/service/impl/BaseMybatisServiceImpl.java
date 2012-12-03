package com.check.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Transactional;

import com.check.bean.Pager;
import com.check.dao.BaseMybatisDao;
import com.check.service.BaseMybatisService;


/**
 * Service实现类 - Service实现类基类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2C6B29872C5AE19E73A129BBF01DFF82
 * ============================================================================
 */

@Transactional
public class BaseMybatisServiceImpl<T, PK extends Serializable> implements BaseMybatisService<T, PK> {

	private BaseMybatisDao<T, PK> baseMybatisDao;


	
	public BaseMybatisDao<T, PK> getBaseMybatisDao() {
		return baseMybatisDao;
	}

	public void setBaseMybatisDao(BaseMybatisDao<T, PK> baseMybatisDao) {
		this.baseMybatisDao = baseMybatisDao;
	}

	@Transactional(readOnly = true)
	public T get(PK id) {
		return (T) baseMybatisDao.get(id);
	}

	@Transactional(readOnly = true)
	public T load(PK id) {
		return (T) baseMybatisDao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> getAllList() {
		return baseMybatisDao.getAllList();
	}
	
	@Transactional(readOnly = true)
	public Integer getTotalCount() {
		return baseMybatisDao.getTotalCount();
	}

	@Transactional
	public PK save(T entity) {
		return (PK) baseMybatisDao.save(entity);
	}

	@Transactional
	public void update(T entity) {
		baseMybatisDao.update(entity);
	}
	
	@Transactional
	public void delete(T entity) {
		baseMybatisDao.delete(entity);
	}

	@Transactional
	public void delete(PK id) {
		baseMybatisDao.delete(id);
	}

	@Transactional
	public void delete(PK[] ids) {
		baseMybatisDao.delete(ids);
	}

	@Transactional(readOnly = true)
	public void flush() {
		baseMybatisDao.flush();
	}

	@Transactional(readOnly = true)
	public void evict(Object object) {
		baseMybatisDao.evict(object);
	}
	
	@Transactional(readOnly = true)
	public void clear() {
		baseMybatisDao.clear();
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager,Map map) {
		return baseMybatisDao.findPager(pager,map);
	}

}