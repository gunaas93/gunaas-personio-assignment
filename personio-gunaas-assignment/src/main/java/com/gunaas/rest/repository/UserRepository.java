package com.gunaas.rest.repository;


import com.gunaas.rest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserEntityByUserName(String userName);
}