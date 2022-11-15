package com.sagecodefellowship.codefellowship.repositories;

import com.sagecodefellowship.codefellowship.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<UserModel, Long> {
    public UserModel findByUsername(String username);
}
