package com.example.TalanCDZ.mail;

import com.example.TalanCDZ.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,  foreignKey = @ForeignKey(name = "my_constraint", value = ConstraintMode.NO_CONSTRAINT))
    private User user;
}
