package com.sanctionapp.DAO;

import com.sanctionapp.entity.Person;

import java.util.List;

public interface PersonDAO {

    Person findById(Long id);
    List<Person> searchByFullName(String searchTerm);

    public List<Person> searchByFirstName(String firstName, String lastName);
}
