package com.pawith.userdomain.repository;

import com.pawith.userdomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select u from User u where u.id in :ids")
    List<User> findAllByIds(List<Long> ids);

    @Query("select u from User u where u.nickname = :nickname and u.id in :ids")
    List<User> findAllByNicknameAndIds(String nickname, List<Long> ids);

}