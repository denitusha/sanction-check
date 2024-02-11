package com.sanctionapp.DAO;

import com.sanctionapp.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PersonDAOImpl implements PersonDAO{


    private final EntityManager entityManager;
    @Override
    public Person findById(Long id) {
        return entityManager.find(Person.class, id);
    }

    public List<Person> searchByFullName(String searchTerm) {
        // Perform search using Hibernate Search
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Person> result = searchSession.search(Person.class)
                .where(f -> f.match()
                        .field("fullName")
                        .matching(searchTerm)
                        .fuzzy(2)
                )
                .fetch(20);

        Duration took = result.took();
        Boolean timedOut = result.timedOut();
        System.out.println(took);
        return result.hits();
    }

    public List<Person> searchByFirstName(String firstName, String lastName) {
        // Perform search using Hibernate Search
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Person> result = searchSession.search(Person.class)
                .where(f -> f.bool()
                        .must(
                                f.match()
                                        .field("firstName")
                                        .matching(firstName)
                                        .fuzzy(2)
                                         // Maximum edit distance for firstName
                        )
                        .must(
                                f.match()
                                        .field("lastName")
                                        .matching(lastName)
                                        .fuzzy(2)  // Maximum edit distance for lastName
                        )
                )
                .fetchAll();

        Duration took = result.took();
        Boolean timedOut = result.timedOut();
        System.out.println(took);
        return result.hits();
    }


}
