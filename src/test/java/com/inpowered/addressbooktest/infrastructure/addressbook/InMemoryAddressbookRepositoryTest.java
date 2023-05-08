package com.inpowered.addressbooktest.infrastructure.addressbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.inpowered.addressbooktest.core.addressbook.AddressbookRepository;
import com.inpowered.addressbooktest.core.person.Gender;
import com.inpowered.addressbooktest.core.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryAddressbookRepositoryTest {

    Person person1;
    Person person2;
    AddressbookRepository inMemoryAddressbookRepository;

    @BeforeEach
    void setup() {
        Date date = new Date();
        person1 = Person.builder()
                .name("p1")
                .gender(Gender.MALE)
                .birthDate(date)
                .build();
        person2 = Person.builder()
                .name("p2")
                .gender(Gender.FEMALE)
                .birthDate(date)
                .build();

        inMemoryAddressbookRepository = new InMemoryAddressbookRepository();
    }

    @Test
    void findAllEmpty() {
        int expected = 0;
        int result = inMemoryAddressbookRepository.findAll().size();
        assertEquals(expected, result);
    }

    @Test
    void addAndFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        inMemoryAddressbookRepository.add(id1, person1);
        inMemoryAddressbookRepository.add(id2, person2);

        int expected = 2;
        int result = inMemoryAddressbookRepository.findAll().size();
        assertEquals(expected, result);
    }

    @Test
    void getOldestPerson() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        inMemoryAddressbookRepository.add(id1, person1);
        inMemoryAddressbookRepository.add(id2, person2);

        Optional<Person> oldestPerson = inMemoryAddressbookRepository.getOldestPerson();
        assertTrue(oldestPerson.isPresent());
    }

    @Test
    void getTotalByGender() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        inMemoryAddressbookRepository.add(id1, person1);
        inMemoryAddressbookRepository.add(id2, person2);
        inMemoryAddressbookRepository.add(id3, person2);

        assertEquals(1, inMemoryAddressbookRepository.getTotalByGender(Gender.MALE));
        assertEquals(2, inMemoryAddressbookRepository.getTotalByGender(Gender.FEMALE));
    }
}
