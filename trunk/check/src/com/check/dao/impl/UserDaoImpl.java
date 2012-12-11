package com.check.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.check.bean.User;
import com.check.dao.UserDao;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseMybatisDaoImpl<User, String> implements
		UserDao {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	public User checkUser(String orderNo) {
		try {
			return (User) this.getSqlSessionTemplate().selectOne("User.login",
					orderNo);
		} catch (RuntimeException re) {
			logger.error(re);
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public User adminLogin(String username, String password) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username", username);
		map.put("password", password);
		try {
			return (User) this.getSqlSessionTemplate().selectOne("User.adminLogin",map);
		} catch (RuntimeException re) {
			logger.error(re);
			re.printStackTrace();
			throw re;
		}
	}
}
