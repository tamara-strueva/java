package com.example.todo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.models.User;
import com.example.todo.services.UserService;

@RestController // аннотация контроллера
@RequestMapping("/users") // по такому адресу будут производиться запросы
public class UserController {
    @Autowired
    private UserService userService;

    // добавление пользователя (с клиента на сервер)
    @PostMapping("/")
    public void save(@RequestBody User user) {
        userService.saveUser(user);
    }

    // получение списка всех пользователей (с сервера на клиента)
    @GetMapping("")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    // удаление пользователя
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    // получение данных с сервера про 1 клиента по id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    // получение данных с сервера про 1 клиента по имени
    @GetMapping("/{name}")
    public List<User> getByFirstName(@PathVariable String firstName) {
        return userService.getUsersByFirstName(firstName);
    }

    // редактирование клиента (1 или нескольких атрибутов) по id
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
        try{
            User baseUser = userService.getUserById(id);
            baseUser.updateUser(user);
            userService.saveUser(baseUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
