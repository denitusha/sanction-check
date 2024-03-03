package com.sanctionapp.DAO;

import com.sanctionapp.entity.SearchLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchLogsRepository extends JpaRepository<SearchLogs, Long> {

}
