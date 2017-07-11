package com.whg.backend.repo.impl;

import org.springframework.stereotype.Repository;

import com.whg.backend.bo.user.User;
import com.whg.backend.repo.UserRepo;

@Repository("userRepo")
public class UserRepoImpl implements UserRepo {

	@Override
	public User findUser(long userId) {
		return new User();
	}

}
