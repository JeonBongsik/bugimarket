package com.bugimarket.user.repository;

import com.bugimarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    //boolean existsByNickName(String nickName);

    User findByEmail(String email);

}