package com.ionix.assessment.repository;

import com.ionix.assessment.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByEmail(String email);

    Integer deleteUserEntityByEmail(String email);
}
