package com.example.csv.services;

import com.example.csv.DTO.UserDTO;
import com.example.csv.domain.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<String> getAttributes();
    User save(User user);

    List<User> getAllUsers();

    User getUser(Integer id);
    Page<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    List<User> searchUsers(String searchTerm);

    Optional<User> update(Integer id, UserDTO user);
    void delete(Integer id);


}
