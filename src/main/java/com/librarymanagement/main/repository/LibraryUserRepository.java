package com.librarymanagement.main.repository;

import com.librarymanagement.main.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {
    LibraryUser findByUserName(String userName);
}
