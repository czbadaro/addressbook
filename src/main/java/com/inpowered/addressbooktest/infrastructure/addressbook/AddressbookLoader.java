package com.inpowered.addressbooktest.infrastructure.addressbook;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.inpowered.addressbooktest.core.addressbook.AddressbookRepository;
import com.inpowered.addressbooktest.core.person.Gender;
import com.inpowered.addressbooktest.core.person.Person;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AddressbookLoader {

    private final ApplicationContext applicationContext;
    private final AddressbookRepository addressbookRepository;
    private final CsvMapper csvMapper;

    public AddressbookLoader(
            @NonNull ApplicationContext applicationContext,
            @NonNull AddressbookRepository addressbookRepository,
            @NonNull CsvMapper csvMapper) {
        this.applicationContext = applicationContext;
        this.addressbookRepository = addressbookRepository;
        this.csvMapper = csvMapper;
    }

    @PostConstruct
    public void load() {
        log.info("Start loading addressbook file");

        Arrays.stream(loadFromResources())
                .filter(Resource::exists)
                .map(this::toPersonList)
                .findFirst()
                .orElseThrow()
                .forEach(person -> addressbookRepository.add(UUID.randomUUID(), person));

        log.info("Finished loading addressbook!");

        calculateAndLogAnswers();
    }

    private void calculateAndLogAnswers() {
        Person person1 = addressbookRepository.findAll().stream()
                .filter(person -> person.getName().contains("Bill McKnight"))
                .findFirst()
                .orElseThrow();

        Person person2 = addressbookRepository.findAll().stream()
                .filter(person -> person.getName().contains("Paul Robinson"))
                .findFirst()
                .orElseThrow();

        Duration difference = Person.getAgeDifferenceBetween(person1, person2);

        log.info("1- THERE ARE {} MALE PEOPLE", addressbookRepository.getTotalByGender(Gender.MALE));
        log.info("2- THE OLDEST PERSON IS {}", addressbookRepository.getOldestPerson().orElseThrow().getName());
        log.info("3- {} IS {} DAYS OLDER THAN {}", person1.getName(), difference.toDays(), person2.getName());

    }

    private Resource[] loadFromResources() {
        String fileDirectory = "classpath*:addressbook/AddressBook.txt";
        try {
            log.info("Starting loading from resources");

            Resource[] resources = applicationContext.getResources(fileDirectory);

            log.info("Finished load from resources! Found {} addressbook file", resources.length);
            return resources;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load addressbook file", exception);
        }
    }

    private List<Person> toPersonList(Resource resource) {
        try {
            log.info("Trying to parse file as a List of Person");

            CsvSchema schema = csvMapper.typedSchemaFor(Person.class)
                    .withColumnSeparator(',');

            MappingIterator<Person> personIterator = csvMapper
                    .readerWithTypedSchemaFor(Person.class)
                    .readValues(resource.getInputStream());

            log.info("Finished parsing file as a List of Person!");
            return personIterator.readAll();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse file as a List of Person", exception);
        }
    }

}
