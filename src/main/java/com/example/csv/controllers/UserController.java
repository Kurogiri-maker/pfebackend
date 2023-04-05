package com.example.csv.controllers;


import com.example.csv.DTO.UserDTO;
import com.example.csv.domain.Tiers;
import com.example.csv.domain.User;
import com.example.csv.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;



    @GetMapping("/attributes")
    public ResponseEntity<List<String>> getAttributes(){
        List<String> attributes = userService.getAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        user.setPassword(userService.passwordEncoder(user.getPassword()));
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        Page<User> users = userService.getAllUsers(page,size,sortBy);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/users/find")
    public ResponseEntity<List<User>> getUserByNom(@RequestParam(required = false) String searchTerm){
        List<User> users = userService.searchUsers(searchTerm);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO user){
        Optional<User>user1 = userService.update(id,user);
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }


}
