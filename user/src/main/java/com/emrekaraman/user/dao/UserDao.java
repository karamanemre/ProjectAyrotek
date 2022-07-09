package com.emrekaraman.user.dao;

import com.emrekaraman.user.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao  extends JpaRepository<Seller,Long> {
    boolean existsByEmail(String email);
    Seller findByEmail(String email);
}
