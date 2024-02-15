package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.exception.UserNotFoundException;
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
        return libraryUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    @Override
    public LibraryUser saveUser(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (!libraryUserRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        libraryUserRepository.deleteById(userId);
    }

    @Override
    public LibraryUser updateUserDetails(Integer userId, LibraryUser updatedUser) {
        LibraryUser user = libraryUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        // Update user details
        user.setUserName(updatedUser.getUserName());
        user.setUserEmail(updatedUser.getUserEmail());
        user.setUserPhone(updatedUser.getUserPhone());
        return libraryUserRepository.save(user);
    }

    @Override
    public LibraryUser getUserByName(String name) {
        LibraryUser user = libraryUserRepository.findByUserName(name);
        if (user == null) {
            throw new UserNotFoundException("User with name " + name + " not found");
        }
        return user;
    }
}
