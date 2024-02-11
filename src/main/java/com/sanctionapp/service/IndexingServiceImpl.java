package com.sanctionapp.service;

import com.sanctionapp.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService{


    private final EntityManager entityManager;

    @Transactional
    public void indexExistingData()  {
        // Get a Hibernate Search session from the EntityManager
        SearchSession searchSession = Search.session(entityManager);

        // Create a MassIndexer for the Person entity
        MassIndexer indexer = searchSession.massIndexer(Person.class)
                .threadsToLoadObjects(4); // Set the number of threads to load objects

        // Start the batch indexing process and wait for it to complete
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            // Handle interruption if necessary
            Thread.currentThread().interrupt();
            // Log or handle the exception
            e.printStackTrace();
        }
    }
}
