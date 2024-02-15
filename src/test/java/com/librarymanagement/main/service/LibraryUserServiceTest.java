package com.librarymanagement.main.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.repository.LibraryUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private LibraryUserServiceImpl libraryUserService;

    @Test
    public void testGetAllUsers() {
        List<LibraryUser> expectedLibraryUsers = Arrays.asList(new LibraryUser(1, "Rohit", "rohit@gmail.com","9293847565"), new LibraryUser(2, "Sachin", "sachin112@gmail.com","8272625242"));
        when(libraryUserRepository.findAll()).thenReturn(expectedLibraryUsers);

        List<LibraryUser> actualLibraryUsers = libraryUserService.getAllUsers();

        assertIterableEquals(expectedLibraryUsers, actualLibraryUsers);
    }

    @Test
    public void testGetUserById() {
        Integer userId = 1;
        LibraryUser expectedLibraryUsers = new LibraryUser(1, "Rohit", "rohit@gmail.com","9293847565");
        when(libraryUserRepository.findById(userId)).thenReturn(java.util.Optional.of(expectedLibraryUsers));

        LibraryUser actualLibraryUsers = libraryUserService.getUserById(userId);

        assertEquals(expectedLibraryUsers, actualLibraryUsers);
    }

    @Test
    public void testSaveUser() {
        LibraryUser libraryUser = new LibraryUser(3, "Gaurav",  "gaurav@gmail.com","9876543210");
        when(libraryUserRepository.save(libraryUser)).thenReturn(libraryUser);

        LibraryUser savedLibraryUser = libraryUserService.saveUser(libraryUser);

        assertEquals(libraryUser, savedLibraryUser);
    }
    
    @Test
    public void testDeleteUser() {
        Integer userId = 1;

        libraryUserService.deleteUser(userId);

        verify(libraryUserRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testUpdateUserDetails() {

        Integer userId = 1;
        LibraryUser existingLibraryUser = new LibraryUser(1, "Rohit", "rohit@gmail.com","9293847565");
        LibraryUser updatedLibraryUser = new LibraryUser(1, "Rohit Jain", "rohit.jain@gmail.com", "9293847565");
        when(libraryUserRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(libraryUserRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        LibraryUser result = libraryUserService.updateUserDetails(userId, updatedLibraryUser);

        assertEquals(updatedLibraryUser, result);
    }

    @Test
    public void testGetUserByName() {
        String userName = "Rohit";
        LibraryUser expectedLibraryUser = new LibraryUser(1, "Rohit", "rohit@gmail.com","9293847565");
        
        when(libraryUserRepository.findByUserName(userName)).thenReturn(expectedLibraryUser);
    
        LibraryUser actualLibraryUser = libraryUserService.getUserByName(userName);
    
        assertEquals(expectedLibraryUser, actualLibraryUser);
    }
    
}
