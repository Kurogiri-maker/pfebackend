package com.example.csv.helper;

import com.example.csv.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> emailContains(String email) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("email")),  email);

    }
    public static Specification<User> firstNameContains(String firstName) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("firstName")),  firstName);
    }

    public static Specification<User> lastNameContains(String lastName) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("lastName")),  lastName);
    }
}
