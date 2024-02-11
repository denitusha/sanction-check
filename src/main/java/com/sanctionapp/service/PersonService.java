package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonDAO;
import com.sanctionapp.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonDAO personDAO;
    public List<Person> searchByFullName(String fullName){
        return personDAO.searchByFullName(fullName);
    }
}
