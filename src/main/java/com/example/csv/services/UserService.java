package com.example.csv.services;

import com.example.csv.DTO.UserDTO;
import com.example.csv.domain.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> getAllUsers();
    Page<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    List<User> searchUsers(String searchTerm);

    void update(Long id, UserDTO user);
    void delete(Long id);


}
