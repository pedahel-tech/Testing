package com.fm.authentication.respositories;

import com.fm.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by sadiq.odho on 1/31/2019.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
    @Query(value = "FROM User WHERE email = :email")
    User findByEmail(@Param("email") String email);
}