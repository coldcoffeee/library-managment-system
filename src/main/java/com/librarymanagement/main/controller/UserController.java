package com.librarymanagement.main.controller;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private LibraryUserService libraryUserService;

    @GetMapping
    public List<LibraryUser> getAllUsers() {
        return libraryUserService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public LibraryUser getUserById(@PathVariable Integer userId) {
        return libraryUserService.getUserById(userId);
    }

    @PostMapping
    public LibraryUser addUser(@RequestBody LibraryUser libraryUser) {
        return libraryUserService.saveUser(libraryUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        libraryUserService.deleteUser(userId);
    }
}
