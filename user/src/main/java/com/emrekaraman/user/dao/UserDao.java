package com.emrekaraman.user.dao;

import com.emrekaraman.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao  extends JpaRepository<User,Long> {
}
