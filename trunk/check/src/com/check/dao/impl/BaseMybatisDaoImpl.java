package com.check.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.check.bean.Pager;
import com.check.dao.BaseMybatisDao;


/**
 * @version 1.0
 * 
 * @author lijunhui
 * 
 *         创建时间：2011-12-31 上午09:42:27
 * 
 *         Dao实现类 - 基类
 */
public class BaseMybatisDaoImpl<T, PK extends Serializable> extends SqlSessionDaoSupport implements BaseMybatisDao<T, PK> {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称

	public Class<T> entityClass;

	public static Logger logger = LoggerFactory.getLogger(BaseMybatisDaoImpl.class);

	@Autowired(required = true)
	@Resource(name = "sqlSessionFactory")
	public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	public BaseMybatisDaoImpl() {
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}


	@SuppressWarnings("unchecked")
	public T get(PK id) {
		try {
			return (T) this.getSqlSessionTemplate().selectOne(entityClass.getSimpleName() + ".findById", id);
		} catch (RuntimeException re) {
			logger.error("findById " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		try {
			return (T) this.getSqlSessionTemplate().selectOne(entityClass.getSimpleName() + ".findById", id);
		} catch (RuntimeException re) {
			logger.error("findById " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	public List<T> getAllList() {
		try {
			return this.getSqlSessionTemplate().selectList(entityClass.getSimpleName() + ".findAll");
		} catch (RuntimeException re) {
			logger.error("findAll " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	public Integer getTotalCount() {
		try {
			return (Integer) getSqlSessionTemplate().selectOne(entityClass.getSimpleName() + ".getTotalCount");
		} catch (RuntimeException re) {
			logger.error("getTotalCount " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	public PK save(T entity) {
		try {
			getSqlSessionTemplate().insert(entityClass.getSimpleName() + ".save", entity);
			return null;
		} catch (RuntimeException re) {
			logger.error("save " + entityClass.getName() + " failed :{}", re);
			throw re;
		}
	}

	public void update(T entity) {
		try {
			this.getSqlSessionTemplate().update(entityClass.getSimpleName() + ".update", entity);
		} catch (RuntimeException re) {
			logger.error("update " + entityClass.getName() + " failed :{}", re);
			throw re;
		}
	}

	public void delete(T entity) {
		try {
			this.getSqlSessionTemplate().delete(entityClass.getSimpleName() + ".delete", entity);
		} catch (RuntimeException re) {
			logger.error("delete " + entityClass.getName() + " failed :{}", re);
			throw re;
		}
	}

	public void delete(PK id) {
		try {
			this.getSqlSessionTemplate().delete(entityClass.getSimpleName() + ".deleteById", id);
		} catch (RuntimeException re) {
			logger.error("delete " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(PK[] ids) {
		List list = Arrays.asList(ids);
		try {
			this.getSqlSessionTemplate().delete(entityClass.getSimpleName() + ".deleteByArray", list);
		} catch (RuntimeException re) {
			logger.error("delete " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	public void flush() {
		// getSession().flush();
	}

	public void evict(Object object) {
		// Assert.notNull(object, "object is required");
		// getSession().evict(object);
	}

	public void clear() {
		// getSession().clear();
	}

	@SuppressWarnings("unchecked")
	public Pager findPager(Pager pager,Map map) {
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize(); // 总页数
		String orderBy = pager.getOrderBy();
		Pager.Order order = pager.getOrder();
		map.put("pageNumber", (pageNumber-1)*pageSize);
		map.put("pageSize", pageSize);
		
		pager.setTotalCount(criteriaResultTotalCount(map));
		if (!StringUtils.equals(orderBy, ORDER_LIST_PROPERTY_NAME)) {
			if (StringUtils.isEmpty(orderBy) || order == null) {
				pager.setOrderBy(ORDER_LIST_PROPERTY_NAME);
				pager.setOrder(Pager.Order.asc);
			}
		} else if (!StringUtils.equals(orderBy, CREATE_DATE_PROPERTY_NAME) ) {
			if (StringUtils.isEmpty(orderBy) || order == null) {
				pager.setOrderBy(CREATE_DATE_PROPERTY_NAME);
				pager.setOrder(Pager.Order.desc);
			}
		}
		// 设置当前页不能大于总页数
		Integer pageCount = pager.getTotalCount() % pageSize == 0 ? pager.getTotalCount() / pageSize : (pager.getTotalCount() / pageSize + 1);
		if (pager.getPageNumber() > pageCount) {
			pager.setPageNumber(pageCount);
		}
		pager.setResult(this.getSqlSessionTemplate().selectList(entityClass.getSimpleName() + ".queryPage", map));
		return pager;
	}



	// 获取Criteria查询数量
	@SuppressWarnings("unchecked")
	private int criteriaResultTotalCount(Map map) {
		try {
			return  (Integer)getSqlSessionTemplate().selectOne(entityClass.getSimpleName() + ".getTotalCounts",map);
		} catch (RuntimeException re) {
			logger.error("getTotalCounts " + entityClass.getName() + "failed :{}", re);
			throw re;
		}
	}

	
}