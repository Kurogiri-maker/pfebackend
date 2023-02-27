package com.example.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tutorials")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Tutorial {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private boolean published;

}
