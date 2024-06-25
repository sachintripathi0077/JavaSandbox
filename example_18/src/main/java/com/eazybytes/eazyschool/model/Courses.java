package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter

public class Courses extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int courseId;

    private String name;
    private String fees;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();
}
