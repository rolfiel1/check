package com.check.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.check.bean.Pager;

/**
 * Service接口 - Service接口基类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF02A2641DDF4DDD4BBBA1E8E3A3E2860
 * ============================================================================
 */

public interface BaseMybatisService<T, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);

	/**
	 * 获取所有实体对象集合
	 * @return 实体对象集合
	 */
	public List<T> getAllList();
	
	/**
	 * 获取所有实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	public Integer getTotalCount();

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @param string 
	 * @return ID
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);
	
	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session
	 * 
	 */
	public void flush();

	/**
	 * 清除对象
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);
	
	/**
	 * 清除Session
	 * 
	 */
	public void clear();
	
	/**
	 * 根据Pager进行查询(提供分页、查找、排序功能)
	 * 
	 * @param pager
	 *            Pager对象
	 * 
	 * @return Pager对象
	 */
	@SuppressWarnings("unchecked")
	public Pager findPager(Pager pager, Map map);

}