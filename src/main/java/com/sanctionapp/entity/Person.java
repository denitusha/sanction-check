package com.sanctionapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;



@Entity(name = "Person")
@Data
@Indexed
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="full_name")
    @FullTextField
    private String fullName;

    @Column(name="first_name")
    @FullTextField
    private String firstName;

    @Column(name="last_name")
    @FullTextField
    private String lastName;

    @Column(name = "gender")
    @KeywordField
    private String gender;

    @Column(name = "country")
    @KeywordField
    private String country;

}
