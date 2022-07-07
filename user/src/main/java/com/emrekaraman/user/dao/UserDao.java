package com.emrekaraman.user.dao;

import com.emrekaraman.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao  extends MongoRepository<User,String> {
}
