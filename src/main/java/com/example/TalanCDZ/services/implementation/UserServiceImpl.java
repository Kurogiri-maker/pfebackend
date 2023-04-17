package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.UserDTO;
import com.example.TalanCDZ.domain.User;
import com.example.TalanCDZ.helper.UserSpecifications;
import com.example.TalanCDZ.helper.mapper.UserMapper;
import com.example.TalanCDZ.repositories.UserRepository;
import com.example.TalanCDZ.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper;

    @Override
    public List<String> getAttributes() {
        return List.of("id","firstName", "lastName","password", "email", "role", "enabled");
    }

    @Override
    public User save(User user) {
        User user1 = userRepository.save(user);
        return user1;
    }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }



    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Page<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return Page.empty();
        }
    }

    @Override
    public List<User> searchUsers(String searchTerm) {
        Specification<User> spec = Specification.where(
                UserSpecifications.firstNameContains(searchTerm))
                .or(UserSpecifications.lastNameContains(searchTerm))
                .or(UserSpecifications.emailContains(searchTerm));

        return userRepository.findAll(spec);
    }

    @Override
    public Optional<User> update(Long id, UserDTO dto) {
        User u= userRepository.findById(id).get();
        if (u!=null){
            User u1 = mapper.mapNonNullFields(dto,u);
            System.out.println(u1);
            userRepository.save(u1);
            return Optional.of(u1);
        }
        else return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String passwordEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
