package com.example.s7.repository;

import com.example.s7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User, Object> {
    @Query(
    value = "select * from user inner join friends on user_id=request_id or user_id=address_id where friends_id in"
            + " (:id) and user.name<>:name", nativeQuery = true)
    Iterable<User> findFriendById(@Param("id") List<Integer> id, @Param("name") String userName);
    User findByName(String name);
    Boolean existsByLogin(String login);
    User findByLogin(String login);
}
