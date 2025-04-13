package com.devsenior.library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.User;
import com.devsenior.library.repository.UserRepository;


public class UserService {
    private final UserRepository userRepository;

    // Constructor sin argumentos
    public UserService() {
        this.userRepository = new UserRepository(); // Inicialización del repositorio
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<User> users = new ArrayList<>();

    public void addUser(String id, String name, String email) {
        userRepository.save(new User(id, name, email, LocalDate.now()));
    }

    public User getUserById(String id) throws NotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Usuario con ID " + id + " no encontrado.");
        }
        return user;
    }

    public void addUser(String id, String name, String email, LocalDate registerDate) {
        users.add(new User(id, name, email, registerDate));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserEmail(String id, String email) throws NotFoundException {
        User user = getUserById(id);
        user.setEmail(email);
        userRepository.save(user);
    }

    public void updateUserName(String id, String name) throws NotFoundException {
        User user = getUserById(id);
        user.setName(name);
        userRepository.save(user);
    }

    public void deleteUser(String id) throws NotFoundException {
        if (getUserById(id) != null) {
            userRepository.deleteById(id);
        }
    }

}
