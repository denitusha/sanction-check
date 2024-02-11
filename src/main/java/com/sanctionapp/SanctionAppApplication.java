package com.sanctionapp;


import com.sanctionapp.DAO.PersonDAO;
import com.sanctionapp.entity.Person;
import com.sanctionapp.service.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class SanctionAppApplication implements CommandLineRunner {

//	private final IndexingService indexingService;
//	private final EntityManager entityManager;
	private final PersonDAO personDAO;

	public static void main(String[] args) {
		SpringApplication.run(SanctionAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


//		indexingService.indexExistingData();

		String searchTerm = "AltinHalili"; // Sample search term
		List<Person> searchResults = personDAO.searchByFullName(searchTerm);

		System.out.println("Search results for '" + searchTerm + "':");
		for (Person person : searchResults) {
			System.out.println(person); // Assuming Person class has toString() method defined
		}

		String searchTerm1 = "Altin"; // Sample search term
		String searchTerm2 = "Halili"; // Sample search term
		List<Person> searchResults1 = personDAO.searchByFirstName(searchTerm1, searchTerm2);

		System.out.println("Search results for '" + searchTerm1 + "':");
		for (Person person : searchResults1) {
			System.out.println(person); // Assuming Person class has toString() method defined
		}

		//Person person = personDAO.findById(16L);
		//System.out.println(person);
	}
}
