package com.check.dao;

import com.check.bean.User;

public interface UserDao extends BaseMybatisDao<User, String>{

	public User checkUser(String orderNo);
	
	public User adminLogin(String username, String password);
}
