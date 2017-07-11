package com.whg.backend.repo;

import com.whg.backend.bo.user.User;

public interface UserRepo {

	User findUser(long userId);
	
}
