package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.LibraryUser;

import java.util.List;

public interface LibraryUserService {
    List<LibraryUser> getAllUsers();

    LibraryUser getUserById(Integer userId);

    LibraryUser saveUser(LibraryUser libraryUser);

    void deleteUser(Integer userId);

    LibraryUser updateUserDetails(Integer userId, LibraryUser updatedUser);

    LibraryUser getUserByName(String name);

}
