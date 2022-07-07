package com.emrekaraman.user.business.concretes;

import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }
}
