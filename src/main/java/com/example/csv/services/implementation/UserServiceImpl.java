package com.example.csv.services.implementation;

import com.example.csv.DTO.UserDTO;
import com.example.csv.domain.Tiers;
import com.example.csv.domain.User;
import com.example.csv.helper.TiersSpecifications;
import com.example.csv.helper.UserSpecifications;
import com.example.csv.helper.mapper.UserMapper;
import com.example.csv.repositories.UserRepository;
import com.example.csv.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Override
    public List<String> getAttributes() {
        return List.of("id","firstName", "lastName", "email", "role", "enabled");
    }

    @Override
    public User save(User user) {
        User user1 = userRepository.save(user);
        return user1;
    }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }



    @Override
    public User getUser(Integer id) {
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
    public Optional<User> update(Integer id, UserDTO dto) {
        User u= userRepository.findById(id).get();
        if (u!=null){
            User u1 = mapper.mapNonNullFields(dto,u);
            userRepository.save(u1);
            return Optional.of(u1);
        }
        else return Optional.empty();
    }


    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
