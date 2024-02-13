package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @Override
    public List<LibraryUser> getAllUsers() {
        return libraryUserRepository.findAll();
    }

    @Override
    public LibraryUser getUserById(Integer userId) {
        return libraryUserRepository.findById(userId).orElse(null);
    }

    @Override
    public LibraryUser saveUser(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        libraryUserRepository.deleteById(userId);
    }
}
