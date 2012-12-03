package com.check.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.bean.User;
import com.check.dao.BaseMybatisDao;
import com.check.dao.UserDao;
import com.check.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseMybatisServiceImpl<User, String> implements UserService{
	@Resource(name = "userDaoImpl")
	public void setBaseDao(BaseMybatisDao<User, String> userDao) {
		super.setBaseMybatisDao(userDao);
	}
	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	public User checkUser(String orderNo) {
		return userDao.checkUser(orderNo);
	}
}
