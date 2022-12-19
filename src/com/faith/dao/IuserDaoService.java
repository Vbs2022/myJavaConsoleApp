package com.faith.dao;

import com.faith.bean.User;

public interface IuserDaoService {
	//check credentials
	public boolean checkCredentials(User user) throws Exception;

	public int getRoleIdFrom(User user)throws Exception;
}
