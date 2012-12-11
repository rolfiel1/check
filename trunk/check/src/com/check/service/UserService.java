package com.check.service;

import com.check.bean.User;

public interface UserService extends BaseMybatisService<User, String> {
	public User checkUser(String orderNo);
	public User adminLogin(String username,String password);
}
