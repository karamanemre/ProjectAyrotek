package com.emrekaraman.user.auth;

import com.emrekaraman.user.dao.UserDao;
import com.emrekaraman.user.entity.Seller;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userDao.existsByEmail(email)){
            Seller seller = userDao.findByEmail(email);
            return new User(seller.getEmail(),seller.getPassword(),new ArrayList<>());
        }
        throw new UsernameNotFoundException(email);
    }
}
