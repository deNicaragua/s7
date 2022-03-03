package com.example.s7.repository;

import com.example.s7.model.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FriendsRepository extends JpaRepository<UserFriends, Object> {
    UserFriends findByRequestIdAndAddressId(Integer requestId, Integer addressId);
    @Query(value ="select friends_id from friends inner join user on user_id=request_id or user_id=address_id " +
            "where user_id=:id", nativeQuery = true)
    List<Integer> findIdAllFriends(@Param("id")Integer id);
}
