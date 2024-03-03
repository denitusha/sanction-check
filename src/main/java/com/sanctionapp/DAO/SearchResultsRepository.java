package com.sanctionapp.DAO;

import com.sanctionapp.entity.Person;
import com.sanctionapp.entity.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchResultsRepository extends JpaRepository<SearchResult, Long> {


}
